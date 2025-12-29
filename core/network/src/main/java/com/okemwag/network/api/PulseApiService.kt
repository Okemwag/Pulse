package com.okemwag.network.api

import com.okemwag.network.model.*
import retrofit2.http.*

/**
 * Pulse API Service
 * 
 * Defines all REST API endpoints for the Pulse community platform.
 */
interface PulseApiService {

    // ==================== News ====================
    
    @GET("news")
    suspend fun getNews(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("category") category: String? = null
    ): ApiResponse<PaginatedResponse<NewsDto>>
    
    @GET("news/{id}")
    suspend fun getNewsById(
        @Path("id") id: String
    ): ApiResponse<NewsDto>
    
    @POST("news")
    suspend fun createNews(
        @Body request: CreateNewsRequest
    ): ApiResponse<NewsDto>
    
    @DELETE("news/{id}")
    suspend fun deleteNews(
        @Path("id") id: String
    ): ApiResponse<Unit>
    
    @POST("news/{id}/like")
    suspend fun likeNews(
        @Path("id") id: String
    ): ApiResponse<Unit>
    
    // ==================== Alerts ====================
    
    @GET("alerts")
    suspend fun getAlerts(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("active_only") activeOnly: Boolean = true
    ): ApiResponse<PaginatedResponse<AlertDto>>
    
    @GET("alerts/{id}")
    suspend fun getAlertById(
        @Path("id") id: String
    ): ApiResponse<AlertDto>
    
    @POST("alerts")
    suspend fun createAlert(
        @Body request: CreateAlertRequest
    ): ApiResponse<AlertDto>
    
    @GET("alerts/nearby")
    suspend fun getNearbyAlerts(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radiusKm: Int = 10
    ): ApiResponse<List<AlertDto>>
    
    // ==================== Classifieds ====================
    
    @GET("classifieds")
    suspend fun getClassifieds(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("category") category: String? = null
    ): ApiResponse<PaginatedResponse<ClassifiedDto>>
    
    @GET("classifieds/{id}")
    suspend fun getClassifiedById(
        @Path("id") id: String
    ): ApiResponse<ClassifiedDto>
    
    // ==================== User ====================
    
    @GET("users/me")
    suspend fun getCurrentUser(): ApiResponse<UserDto>
    
    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): ApiResponse<UserDto>
    
    @PUT("users/me")
    suspend fun updateProfile(
        @Body user: Map<String, String>
    ): ApiResponse<UserDto>
    
    // ==================== Auth ====================
    
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body refreshToken: Map<String, String>
    ): ApiResponse<AuthTokensDto>
    
    // ==================== Wallet ====================
    
    @GET("wallet/balance")
    suspend fun getTokenBalance(): ApiResponse<Long>
    
    @GET("wallet/transactions")
    suspend fun getTransactions(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): ApiResponse<PaginatedResponse<Map<String, Any>>>
}
