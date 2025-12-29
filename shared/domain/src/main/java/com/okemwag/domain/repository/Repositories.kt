package com.okemwag.domain.repository

import com.okemwag.domain.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Repository Interfaces
 * 
 * Define contracts for data access. Implementations will be in the data module.
 */

/**
 * News Repository Interface
 */
interface NewsRepository {
    
    fun getAllNews(): Flow<List<News>>
    
    fun getNewsByCategory(category: NewsCategory): Flow<List<News>>
    
    suspend fun getNewsById(id: String): Result<News>
    
    fun observeNewsById(id: String): Flow<News?>
    
    suspend fun createNews(title: String, content: String, category: NewsCategory, imageUrl: String?): Result<News>
    
    suspend fun likeNews(id: String): Result<Unit>
    
    suspend fun deleteNews(id: String): Result<Unit>
    
    suspend fun refreshNews(): Result<Unit>
}

/**
 * Alert Repository Interface
 */
interface AlertRepository {
    
    fun getActiveAlerts(): Flow<List<Alert>>
    
    fun getAllAlerts(): Flow<List<Alert>>
    
    suspend fun getAlertById(id: String): Result<Alert>
    
    fun observeAlertById(id: String): Flow<Alert?>
    
    suspend fun createAlert(
        title: String,
        description: String,
        type: AlertType,
        severity: AlertSeverity,
        location: Location?
    ): Result<Alert>
    
    suspend fun getNearbyAlerts(latitude: Double, longitude: Double, radiusKm: Int): Result<List<Alert>>
    
    suspend fun refreshAlerts(): Result<Unit>
}

/**
 * User Repository Interface
 */
interface UserRepository {
    
    fun getCurrentUser(): Flow<User?>
    
    suspend fun getCurrentUserSync(): Result<User>
    
    suspend fun getUserById(id: String): Result<User>
    
    fun observeUserById(id: String): Flow<User?>
    
    suspend fun updateProfile(displayName: String?, avatarUrl: String?): Result<User>
    
    suspend fun getTokenBalance(): Result<Long>
    
    suspend fun refreshCurrentUser(): Result<Unit>
    
    suspend fun logout()
}

/**
 * Classified Repository Interface
 */
interface ClassifiedRepository {
    
    fun getActiveClassifieds(): Flow<List<Classified>>
    
    fun getClassifiedsByCategory(category: ClassifiedCategory): Flow<List<Classified>>
    
    suspend fun getClassifiedById(id: String): Result<Classified>
    
    suspend fun createClassified(
        title: String,
        description: String,
        price: Double?,
        currency: String,
        category: ClassifiedCategory,
        images: List<String>,
        location: Location?
    ): Result<Classified>
    
    suspend fun refreshClassifieds(): Result<Unit>
}

/**
 * Draft Repository Interface
 */
interface DraftRepository {
    
    fun getAllDrafts(): Flow<List<Draft>>
    
    fun getDraftsByType(type: DraftType): Flow<List<Draft>>
    
    suspend fun getDraftById(id: Long): Result<Draft>
    
    suspend fun saveDraft(draft: Draft): Result<Long>
    
    suspend fun deleteDraft(id: Long): Result<Unit>
}
