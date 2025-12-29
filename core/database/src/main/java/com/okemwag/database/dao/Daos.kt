package com.okemwag.database.dao

import androidx.room.*
import com.okemwag.database.entity.*
import kotlinx.coroutines.flow.Flow

/**
 * News DAO
 */
@Dao
interface NewsDao {
    
    @Query("SELECT * FROM news ORDER BY createdAt DESC")
    fun getAllNews(): Flow<List<NewsEntity>>
    
    @Query("SELECT * FROM news WHERE category = :category ORDER BY createdAt DESC")
    fun getNewsByCategory(category: String): Flow<List<NewsEntity>>
    
    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getNewsById(id: String): NewsEntity?
    
    @Query("SELECT * FROM news WHERE id = :id")
    fun observeNewsById(id: String): Flow<NewsEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNews(news: List<NewsEntity>)
    
    @Update
    suspend fun updateNews(news: NewsEntity)
    
    @Delete
    suspend fun deleteNews(news: NewsEntity)
    
    @Query("DELETE FROM news WHERE id = :id")
    suspend fun deleteNewsById(id: String)
    
    @Query("DELETE FROM news")
    suspend fun clearAllNews()
    
    @Query("SELECT * FROM news WHERE isSynced = 0")
    suspend fun getUnsyncedNews(): List<NewsEntity>
}

/**
 * Alert DAO
 */
@Dao
interface AlertDao {
    
    @Query("SELECT * FROM alerts WHERE isActive = 1 ORDER BY createdAt DESC")
    fun getActiveAlerts(): Flow<List<AlertEntity>>
    
    @Query("SELECT * FROM alerts ORDER BY createdAt DESC")
    fun getAllAlerts(): Flow<List<AlertEntity>>
    
    @Query("SELECT * FROM alerts WHERE id = :id")
    suspend fun getAlertById(id: String): AlertEntity?
    
    @Query("SELECT * FROM alerts WHERE id = :id")
    fun observeAlertById(id: String): Flow<AlertEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: AlertEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlerts(alerts: List<AlertEntity>)
    
    @Update
    suspend fun updateAlert(alert: AlertEntity)
    
    @Delete
    suspend fun deleteAlert(alert: AlertEntity)
    
    @Query("DELETE FROM alerts")
    suspend fun clearAllAlerts()
    
    @Query("UPDATE alerts SET isActive = 0 WHERE expiresAt IS NOT NULL AND expiresAt < :currentTime")
    suspend fun deactivateExpiredAlerts(currentTime: Long)
}

/**
 * User DAO
 */
@Dao
interface UserDao {
    
    @Query("SELECT * FROM users WHERE isCurrentUser = 1 LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>
    
    @Query("SELECT * FROM users WHERE isCurrentUser = 1 LIMIT 1")
    suspend fun getCurrentUserSync(): UserEntity?
    
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: String): UserEntity?
    
    @Query("SELECT * FROM users WHERE id = :id")
    fun observeUserById(id: String): Flow<UserEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Query("UPDATE users SET isCurrentUser = 0")
    suspend fun clearCurrentUser()
    
    @Query("DELETE FROM users")
    suspend fun clearAllUsers()
    
    @Query("UPDATE users SET tokenBalance = :balance WHERE isCurrentUser = 1")
    suspend fun updateTokenBalance(balance: Long)
}

/**
 * Classified DAO
 */
@Dao
interface ClassifiedDao {
    
    @Query("SELECT * FROM classifieds WHERE isActive = 1 ORDER BY createdAt DESC")
    fun getActiveClassifieds(): Flow<List<ClassifiedEntity>>
    
    @Query("SELECT * FROM classifieds WHERE category = :category AND isActive = 1 ORDER BY createdAt DESC")
    fun getClassifiedsByCategory(category: String): Flow<List<ClassifiedEntity>>
    
    @Query("SELECT * FROM classifieds WHERE id = :id")
    suspend fun getClassifiedById(id: String): ClassifiedEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClassified(classified: ClassifiedEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllClassifieds(classifieds: List<ClassifiedEntity>)
    
    @Update
    suspend fun updateClassified(classified: ClassifiedEntity)
    
    @Delete
    suspend fun deleteClassified(classified: ClassifiedEntity)
    
    @Query("DELETE FROM classifieds")
    suspend fun clearAllClassifieds()
}

/**
 * Draft DAO
 */
@Dao
interface DraftDao {
    
    @Query("SELECT * FROM drafts ORDER BY updatedAt DESC")
    fun getAllDrafts(): Flow<List<DraftEntity>>
    
    @Query("SELECT * FROM drafts WHERE type = :type ORDER BY updatedAt DESC")
    fun getDraftsByType(type: String): Flow<List<DraftEntity>>
    
    @Query("SELECT * FROM drafts WHERE id = :id")
    suspend fun getDraftById(id: Long): DraftEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: DraftEntity): Long
    
    @Update
    suspend fun updateDraft(draft: DraftEntity)
    
    @Delete
    suspend fun deleteDraft(draft: DraftEntity)
    
    @Query("DELETE FROM drafts WHERE id = :id")
    suspend fun deleteDraftById(id: Long)
}
