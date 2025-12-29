package com.okemwag.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * News article entity stored in local database
 */
@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: String,
    val title: String,
    val content: String,
    val authorId: String,
    val authorName: String,
    val imageUrl: String?,
    val category: String,
    val isVerified: Boolean,
    val contentHash: String?,
    val likesCount: Int,
    val commentsCount: Int,
    val createdAt: Long,
    val updatedAt: Long,
    val isSynced: Boolean = true
)

/**
 * Alert entity stored in local database
 */
@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val type: String,
    val severity: Int,
    val latitude: Double?,
    val longitude: Double?,
    val address: String?,
    val radiusMeters: Int?,
    val authorId: String,
    val isActive: Boolean,
    val expiresAt: Long?,
    val createdAt: Long,
    val isSynced: Boolean = true
)

/**
 * User entity stored in local database
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val displayName: String,
    val email: String?,
    val avatarUrl: String?,
    val walletAddress: String?,
    val tokenBalance: Long,
    val reputationScore: Int,
    val isVerified: Boolean,
    val createdAt: Long,
    val isCurrentUser: Boolean = false
)

/**
 * Classified listing entity stored in local database
 */
@Entity(tableName = "classifieds")
data class ClassifiedEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val price: Double?,
    val currency: String,
    val category: String,
    val images: String, // JSON array stored as string
    val sellerId: String,
    val sellerName: String,
    val latitude: Double?,
    val longitude: Double?,
    val address: String?,
    val isActive: Boolean,
    val createdAt: Long,
    val isSynced: Boolean = true
)

/**
 * Draft entity for offline-first content creation
 */
@Entity(tableName = "drafts")
data class DraftEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // news, alert, classified
    val title: String,
    val content: String,
    val category: String?,
    val imageUrl: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
