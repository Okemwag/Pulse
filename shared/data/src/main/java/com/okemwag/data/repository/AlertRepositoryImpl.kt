package com.okemwag.data.repository

import com.okemwag.data.mapper.*
import com.okemwag.database.dao.AlertDao
import com.okemwag.domain.model.*
import com.okemwag.domain.repository.AlertRepository
import com.okemwag.network.api.PulseApiService
import com.okemwag.network.model.CreateAlertRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlertRepositoryImpl @Inject constructor(
    private val apiService: PulseApiService,
    private val alertDao: AlertDao
) : AlertRepository {

    override fun getActiveAlerts(): Flow<List<Alert>> {
        return alertDao.getActiveAlerts().map { it.toAlertDomainList() }
    }

    override fun getAllAlerts(): Flow<List<Alert>> {
        return alertDao.getAllAlerts().map { it.toAlertDomainList() }
    }

    override suspend fun getAlertById(id: String): Result<Alert> {
        return try {
            val local = alertDao.getAlertById(id)
            if (local != null) {
                Result.success(local.toDomain())
            } else {
                val response = apiService.getAlertById(id)
                val data = response.data
                if (response.success && data != null) {
                    alertDao.insertAlert(data.toEntity())
                    Result.success(data.toDomain())
                } else {
                    Result.failure(Exception(response.error ?: "Failed to fetch alert"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching alert")
            Result.failure(e)
        }
    }

    override fun observeAlertById(id: String): Flow<Alert?> {
        return alertDao.observeAlertById(id).map { it?.toDomain() }
    }

    override suspend fun createAlert(
        title: String,
        description: String,
        type: AlertType,
        severity: AlertSeverity,
        location: Location?
    ): Result<Alert> {
        return try {
            val request = CreateAlertRequest(
                title = title,
                description = description,
                type = type.value,
                severity = severity.level,
                latitude = location?.latitude,
                longitude = location?.longitude,
                radiusMeters = location?.radiusMeters
            )
            val response = apiService.createAlert(request)
            val data = response.data
            if (response.success && data != null) {
                alertDao.insertAlert(data.toEntity())
                Result.success(data.toDomain())
            } else {
                Result.failure(Exception(response.error ?: "Failed to create alert"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error creating alert")
            Result.failure(e)
        }
    }

    override suspend fun getNearbyAlerts(
        latitude: Double,
        longitude: Double,
        radiusKm: Int
    ): Result<List<Alert>> {
        return try {
            val response = apiService.getNearbyAlerts(latitude, longitude, radiusKm)
            val data = response.data
            if (response.success && data != null) {
                Result.success(data.map { it.toDomain() })
            } else {
                Result.failure(Exception(response.error ?: "Failed to fetch nearby alerts"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching nearby alerts")
            Result.failure(e)
        }
    }

    override suspend fun refreshAlerts(): Result<Unit> {
        return try {
            val response = apiService.getAlerts()
            val data = response.data
            if (response.success && data != null) {
                alertDao.insertAllAlerts(data.items.toAlertEntities())
                alertDao.deactivateExpiredAlerts(System.currentTimeMillis())
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.error ?: "Failed to refresh alerts"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing alerts")
            Result.failure(e)
        }
    }
}
