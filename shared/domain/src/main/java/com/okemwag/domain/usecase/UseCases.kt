package com.okemwag.domain.usecase

import com.okemwag.domain.model.*
import com.okemwag.domain.repository.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use Cases
 * 
 * Business logic encapsulated in single-responsibility classes.
 */

// ==================== News Use Cases ====================

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<News>> = newsRepository.getAllNews()
}

class GetNewsByCategoryUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(category: NewsCategory): Flow<List<News>> = 
        newsRepository.getNewsByCategory(category)
}

class GetNewsDetailUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(id: String): Result<News> = 
        newsRepository.getNewsById(id)
}

class CreateNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        title: String,
        content: String,
        category: NewsCategory,
        imageUrl: String? = null
    ): Result<News> = newsRepository.createNews(title, content, category, imageUrl)
}

class LikeNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> = 
        newsRepository.likeNews(id)
}

class RefreshNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Result<Unit> = 
        newsRepository.refreshNews()
}

// ==================== Alert Use Cases ====================

class GetActiveAlertsUseCase @Inject constructor(
    private val alertRepository: AlertRepository
) {
    operator fun invoke(): Flow<List<Alert>> = alertRepository.getActiveAlerts()
}

class GetAlertDetailUseCase @Inject constructor(
    private val alertRepository: AlertRepository
) {
    suspend operator fun invoke(id: String): Result<Alert> = 
        alertRepository.getAlertById(id)
}

class CreateAlertUseCase @Inject constructor(
    private val alertRepository: AlertRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        type: AlertType,
        severity: AlertSeverity,
        location: Location? = null
    ): Result<Alert> = alertRepository.createAlert(title, description, type, severity, location)
}

class GetNearbyAlertsUseCase @Inject constructor(
    private val alertRepository: AlertRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        radiusKm: Int = 10
    ): Result<List<Alert>> = alertRepository.getNearbyAlerts(latitude, longitude, radiusKm)
}

// ==================== User Use Cases ====================

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<User?> = userRepository.getCurrentUser()
}

class GetTokenBalanceUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Long> = userRepository.getTokenBalance()
}

class UpdateProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        displayName: String? = null,
        avatarUrl: String? = null
    ): Result<User> = userRepository.updateProfile(displayName, avatarUrl)
}

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.logout()
}

// ==================== Classified Use Cases ====================

class GetClassifiedsUseCase @Inject constructor(
    private val classifiedRepository: ClassifiedRepository
) {
    operator fun invoke(): Flow<List<Classified>> = classifiedRepository.getActiveClassifieds()
}

class GetClassifiedsByCategoryUseCase @Inject constructor(
    private val classifiedRepository: ClassifiedRepository
) {
    operator fun invoke(category: ClassifiedCategory): Flow<List<Classified>> = 
        classifiedRepository.getClassifiedsByCategory(category)
}

class GetClassifiedDetailUseCase @Inject constructor(
    private val classifiedRepository: ClassifiedRepository
) {
    suspend operator fun invoke(id: String): Result<Classified> = 
        classifiedRepository.getClassifiedById(id)
}

// ==================== Draft Use Cases ====================

class GetDraftsUseCase @Inject constructor(
    private val draftRepository: DraftRepository
) {
    operator fun invoke(): Flow<List<Draft>> = draftRepository.getAllDrafts()
}

class SaveDraftUseCase @Inject constructor(
    private val draftRepository: DraftRepository
) {
    suspend operator fun invoke(draft: Draft): Result<Long> = 
        draftRepository.saveDraft(draft)
}

class DeleteDraftUseCase @Inject constructor(
    private val draftRepository: DraftRepository
) {
    suspend operator fun invoke(id: Long): Result<Unit> = 
        draftRepository.deleteDraft(id)
}
