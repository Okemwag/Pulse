package com.okemwag.data.repository

import com.okemwag.data.mapper.*
import com.okemwag.database.dao.UserDao
import com.okemwag.domain.model.User
import com.okemwag.domain.repository.UserRepository
import com.okemwag.network.api.PulseApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: PulseApiService,
    private val userDao: UserDao
) : UserRepository {

    override fun getCurrentUser(): Flow<User?> {
        return userDao.getCurrentUser().map { it?.toDomain() }
    }

    override suspend fun getCurrentUserSync(): Result<User> {
        return try {
            val local = userDao.getCurrentUserSync()
            if (local != null) {
                Result.success(local.toDomain())
            } else {
                refreshCurrentUser()
                val refreshed = userDao.getCurrentUserSync()
                if (refreshed != null) {
                    Result.success(refreshed.toDomain())
                } else {
                    Result.failure(Exception("User not found"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error getting current user")
            Result.failure(e)
        }
    }

    override suspend fun getUserById(id: String): Result<User> {
        return try {
            val local = userDao.getUserById(id)
            if (local != null) {
                Result.success(local.toDomain())
            } else {
                val response = apiService.getUserById(id)
                val data = response.data
                if (response.success && data != null) {
                    userDao.insertUser(data.toEntity())
                    Result.success(data.toDomain())
                } else {
                    Result.failure(Exception(response.error ?: "User not found"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching user")
            Result.failure(e)
        }
    }

    override fun observeUserById(id: String): Flow<User?> {
        return userDao.observeUserById(id).map { it?.toDomain() }
    }

    override suspend fun updateProfile(displayName: String?, avatarUrl: String?): Result<User> {
        return try {
            val updates = mutableMapOf<String, String>()
            displayName?.let { updates["display_name"] = it }
            avatarUrl?.let { updates["avatar_url"] = it }
            
            val response = apiService.updateProfile(updates)
            val data = response.data
            if (response.success && data != null) {
                userDao.insertUser(data.toEntity(isCurrentUser = true))
                Result.success(data.toDomain())
            } else {
                Result.failure(Exception(response.error ?: "Failed to update profile"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error updating profile")
            Result.failure(e)
        }
    }

    override suspend fun getTokenBalance(): Result<Long> {
        return try {
            val response = apiService.getTokenBalance()
            val balance = response.data
            if (response.success && balance != null) {
                userDao.updateTokenBalance(balance)
                Result.success(balance)
            } else {
                val local = userDao.getCurrentUserSync()
                if (local != null) {
                    Result.success(local.tokenBalance)
                } else {
                    Result.failure(Exception(response.error ?: "Failed to get balance"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching token balance")
            Result.failure(e)
        }
    }

    override suspend fun refreshCurrentUser(): Result<Unit> {
        return try {
            val response = apiService.getCurrentUser()
            val data = response.data
            if (response.success && data != null) {
                userDao.clearCurrentUser()
                userDao.insertUser(data.toEntity(isCurrentUser = true))
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.error ?: "Failed to refresh user"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing current user")
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        userDao.clearCurrentUser()
        userDao.clearAllUsers()
    }
}
