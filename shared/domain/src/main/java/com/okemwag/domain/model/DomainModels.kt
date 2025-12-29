package com.okemwag.domain.model

/**
 * Domain Models
 * 
 * Clean domain models decoupled from API DTOs and database entities.
 */

/**
 * News article domain model
 */
data class News(
    val id: String,
    val title: String,
    val content: String,
    val authorId: String,
    val authorName: String,
    val imageUrl: String?,
    val category: NewsCategory,
    val isVerified: Boolean,
    val contentHash: String?,
    val likesCount: Int,
    val commentsCount: Int,
    val createdAt: Long,
    val updatedAt: Long
)

enum class NewsCategory(val value: String) {
    LOCAL("local"),
    EVENTS("events"),
    COMMUNITY("community"),
    GOVERNMENT("government"),
    SPORTS("sports"),
    OTHER("other");
    
    companion object {
        fun fromValue(value: String): NewsCategory {
            return entries.find { it.value == value } ?: OTHER
        }
    }
}

/**
 * Alert domain model
 */
data class Alert(
    val id: String,
    val title: String,
    val description: String,
    val type: AlertType,
    val severity: AlertSeverity,
    val location: Location?,
    val authorId: String,
    val isActive: Boolean,
    val expiresAt: Long?,
    val createdAt: Long
)

enum class AlertType(val value: String) {
    EMERGENCY("emergency"),
    WARNING("warning"),
    INFO("info");
    
    companion object {
        fun fromValue(value: String): AlertType {
            return entries.find { it.value == value } ?: INFO
        }
    }
}

enum class AlertSeverity(val level: Int) {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4),
    EXTREME(5);
    
    companion object {
        fun fromLevel(level: Int): AlertSeverity {
            return entries.find { it.level == level } ?: LOW
        }
    }
}

/**
 * Location domain model
 */
data class Location(
    val latitude: Double,
    val longitude: Double,
    val address: String?,
    val radiusMeters: Int?
)

/**
 * User profile domain model
 */
data class User(
    val id: String,
    val username: String,
    val displayName: String,
    val email: String?,
    val avatarUrl: String?,
    val walletAddress: String?,
    val tokenBalance: Long,
    val reputationScore: Int,
    val isVerified: Boolean,
    val createdAt: Long
)

/**
 * Classified listing domain model
 */
data class Classified(
    val id: String,
    val title: String,
    val description: String,
    val price: Double?,
    val currency: String,
    val category: ClassifiedCategory,
    val images: List<String>,
    val sellerId: String,
    val sellerName: String,
    val location: Location?,
    val isActive: Boolean,
    val createdAt: Long
)

enum class ClassifiedCategory(val value: String) {
    FOR_SALE("for_sale"),
    WANTED("wanted"),
    SERVICES("services"),
    JOBS("jobs"),
    HOUSING("housing"),
    VEHICLES("vehicles"),
    OTHER("other");
    
    companion object {
        fun fromValue(value: String): ClassifiedCategory {
            return entries.find { it.value == value } ?: OTHER
        }
    }
}

/**
 * Draft for offline content creation
 */
data class Draft(
    val id: Long,
    val type: DraftType,
    val title: String,
    val content: String,
    val category: String?,
    val imageUrl: String?,
    val createdAt: Long,
    val updatedAt: Long
)

enum class DraftType(val value: String) {
    NEWS("news"),
    ALERT("alert"),
    CLASSIFIED("classified")
}
