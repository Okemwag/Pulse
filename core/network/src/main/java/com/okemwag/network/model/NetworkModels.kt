package com.okemwag.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * API Response wrapper for all endpoints
 */
@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "success") val success: Boolean,
    @Json(name = "data") val data: T?,
    @Json(name = "message") val message: String?,
    @Json(name = "error") val error: String?
)

/**
 * Paginated response wrapper
 */
@JsonClass(generateAdapter = true)
data class PaginatedResponse<T>(
    @Json(name = "items") val items: List<T>,
    @Json(name = "page") val page: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_items") val totalItems: Int
)

/**
 * News article from API
 */
@JsonClass(generateAdapter = true)
data class NewsDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "author_id") val authorId: String,
    @Json(name = "author_name") val authorName: String,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "category") val category: String,
    @Json(name = "is_verified") val isVerified: Boolean,
    @Json(name = "content_hash") val contentHash: String?,
    @Json(name = "likes_count") val likesCount: Int,
    @Json(name = "comments_count") val commentsCount: Int,
    @Json(name = "created_at") val createdAt: Long,
    @Json(name = "updated_at") val updatedAt: Long
)

/**
 * Alert from API
 */
@JsonClass(generateAdapter = true)
data class AlertDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "type") val type: String, // emergency, warning, info
    @Json(name = "severity") val severity: Int, // 1-5
    @Json(name = "location") val location: LocationDto?,
    @Json(name = "author_id") val authorId: String,
    @Json(name = "is_active") val isActive: Boolean,
    @Json(name = "expires_at") val expiresAt: Long?,
    @Json(name = "created_at") val createdAt: Long
)

/**
 * Location data
 */
@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "address") val address: String?,
    @Json(name = "radius_meters") val radiusMeters: Int?
)

/**
 * User profile from API
 */
@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id") val id: String,
    @Json(name = "username") val username: String,
    @Json(name = "display_name") val displayName: String,
    @Json(name = "email") val email: String?,
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "wallet_address") val walletAddress: String?,
    @Json(name = "token_balance") val tokenBalance: Long,
    @Json(name = "reputation_score") val reputationScore: Int,
    @Json(name = "is_verified") val isVerified: Boolean,
    @Json(name = "created_at") val createdAt: Long
)

/**
 * Classified listing from API
 */
@JsonClass(generateAdapter = true)
data class ClassifiedDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "price") val price: Double?,
    @Json(name = "currency") val currency: String,
    @Json(name = "category") val category: String,
    @Json(name = "images") val images: List<String>,
    @Json(name = "seller_id") val sellerId: String,
    @Json(name = "seller_name") val sellerName: String,
    @Json(name = "location") val location: LocationDto?,
    @Json(name = "is_active") val isActive: Boolean,
    @Json(name = "created_at") val createdAt: Long
)

/**
 * Auth tokens
 */
@JsonClass(generateAdapter = true)
data class AuthTokensDto(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String,
    @Json(name = "expires_in") val expiresIn: Long
)

/**
 * Create news request
 */
@JsonClass(generateAdapter = true)
data class CreateNewsRequest(
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "category") val category: String,
    @Json(name = "image_url") val imageUrl: String?
)

/**
 * Create alert request
 */
@JsonClass(generateAdapter = true)
data class CreateAlertRequest(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "type") val type: String,
    @Json(name = "severity") val severity: Int,
    @Json(name = "latitude") val latitude: Double?,
    @Json(name = "longitude") val longitude: Double?,
    @Json(name = "radius_meters") val radiusMeters: Int?
)
