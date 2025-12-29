package com.okemwag.data.repository

import com.okemwag.data.mapper.*
import com.okemwag.database.dao.NewsDao
import com.okemwag.domain.model.News
import com.okemwag.domain.model.NewsCategory
import com.okemwag.domain.repository.NewsRepository
import com.okemwag.network.api.PulseApiService
import com.okemwag.network.model.CreateNewsRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val apiService: PulseApiService,
    private val newsDao: NewsDao
) : NewsRepository {

    override fun getAllNews(): Flow<List<News>> {
        return newsDao.getAllNews().map { it.toNewsDomainList() }
    }

    override fun getNewsByCategory(category: NewsCategory): Flow<List<News>> {
        return newsDao.getNewsByCategory(category.value).map { it.toNewsDomainList() }
    }

    override suspend fun getNewsById(id: String): Result<News> {
        return try {
            // Try local first
            val local = newsDao.getNewsById(id)
            if (local != null) {
                Result.success(local.toDomain())
            } else {
                // Fetch from API
                val response = apiService.getNewsById(id)
                val data = response.data
                if (response.success && data != null) {
                    newsDao.insertNews(data.toEntity())
                    Result.success(data.toDomain())
                } else {
                    Result.failure(Exception(response.error ?: "Failed to fetch news"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching news by id")
            Result.failure(e)
        }
    }

    override fun observeNewsById(id: String): Flow<News?> {
        return newsDao.observeNewsById(id).map { it?.toDomain() }
    }

    override suspend fun createNews(
        title: String,
        content: String,
        category: NewsCategory,
        imageUrl: String?
    ): Result<News> {
        return try {
            val request = CreateNewsRequest(
                title = title,
                content = content,
                category = category.value,
                imageUrl = imageUrl
            )
            val response = apiService.createNews(request)
            val data = response.data
            if (response.success && data != null) {
                newsDao.insertNews(data.toEntity())
                Result.success(data.toDomain())
            } else {
                Result.failure(Exception(response.error ?: "Failed to create news"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error creating news")
            Result.failure(e)
        }
    }

    override suspend fun likeNews(id: String): Result<Unit> {
        return try {
            val response = apiService.likeNews(id)
            if (response.success) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.error ?: "Failed to like news"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error liking news")
            Result.failure(e)
        }
    }

    override suspend fun deleteNews(id: String): Result<Unit> {
        return try {
            val response = apiService.deleteNews(id)
            if (response.success) {
                newsDao.deleteNewsById(id)
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.error ?: "Failed to delete news"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting news")
            Result.failure(e)
        }
    }

    override suspend fun refreshNews(): Result<Unit> {
        return try {
            val response = apiService.getNews()
            val data = response.data
            if (response.success && data != null) {
                newsDao.insertAllNews(data.items.toEntities())
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.error ?: "Failed to refresh news"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing news")
            Result.failure(e)
        }
    }
}
