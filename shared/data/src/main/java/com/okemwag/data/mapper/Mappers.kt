package com.okemwag.data.mapper

import com.okemwag.database.entity.*
import com.okemwag.domain.model.*
import com.okemwag.network.model.*

/**
 * Mappers
 * 
 * Transform data between layers (DTO <-> Entity <-> Domain)
 */

// ==================== News Mappers ====================

fun NewsDto.toEntity(): NewsEntity = NewsEntity(
    id = id,
    title = title,
    content = content,
    authorId = authorId,
    authorName = authorName,
    imageUrl = imageUrl,
    category = category,
    isVerified = isVerified,
    contentHash = contentHash,
    likesCount = likesCount,
    commentsCount = commentsCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isSynced = true
)

fun NewsEntity.toDomain(): News = News(
    id = id,
    title = title,
    content = content,
    authorId = authorId,
    authorName = authorName,
    imageUrl = imageUrl,
    category = NewsCategory.fromValue(category),
    isVerified = isVerified,
    contentHash = contentHash,
    likesCount = likesCount,
    commentsCount = commentsCount,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun NewsDto.toDomain(): News = toEntity().toDomain()

fun List<NewsDto>.toEntities(): List<NewsEntity> = map { it.toEntity() }
fun List<NewsEntity>.toNewsDomainList(): List<News> = map { it.toDomain() }

// ==================== Alert Mappers ====================

fun AlertDto.toEntity(): AlertEntity = AlertEntity(
    id = id,
    title = title,
    description = description,
    type = type,
    severity = severity,
    latitude = location?.latitude,
    longitude = location?.longitude,
    address = location?.address,
    radiusMeters = location?.radiusMeters,
    authorId = authorId,
    isActive = isActive,
    expiresAt = expiresAt,
    createdAt = createdAt,
    isSynced = true
)

fun AlertEntity.toDomain(): Alert = Alert(
    id = id,
    title = title,
    description = description,
    type = AlertType.fromValue(type),
    severity = AlertSeverity.fromLevel(severity),
    location = if (latitude != null && longitude != null) {
        Location(latitude!!, longitude!!, address, radiusMeters)
    } else null,
    authorId = authorId,
    isActive = isActive,
    expiresAt = expiresAt,
    createdAt = createdAt
)

fun AlertDto.toDomain(): Alert = toEntity().toDomain()

fun List<AlertDto>.toAlertEntities(): List<AlertEntity> = map { it.toEntity() }
fun List<AlertEntity>.toAlertDomainList(): List<Alert> = map { it.toDomain() }

// ==================== User Mappers ====================

fun UserDto.toEntity(isCurrentUser: Boolean = false): UserEntity = UserEntity(
    id = id,
    username = username,
    displayName = displayName,
    email = email,
    avatarUrl = avatarUrl,
    walletAddress = walletAddress,
    tokenBalance = tokenBalance,
    reputationScore = reputationScore,
    isVerified = isVerified,
    createdAt = createdAt,
    isCurrentUser = isCurrentUser
)

fun UserEntity.toDomain(): User = User(
    id = id,
    username = username,
    displayName = displayName,
    email = email,
    avatarUrl = avatarUrl,
    walletAddress = walletAddress,
    tokenBalance = tokenBalance,
    reputationScore = reputationScore,
    isVerified = isVerified,
    createdAt = createdAt
)

fun UserDto.toDomain(): User = toEntity().toDomain()

// ==================== Classified Mappers ====================

fun ClassifiedDto.toEntity(): ClassifiedEntity = ClassifiedEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    currency = currency,
    category = category,
    images = images.joinToString(","),
    sellerId = sellerId,
    sellerName = sellerName,
    latitude = location?.latitude,
    longitude = location?.longitude,
    address = location?.address,
    isActive = isActive,
    createdAt = createdAt,
    isSynced = true
)

fun ClassifiedEntity.toDomain(): Classified = Classified(
    id = id,
    title = title,
    description = description,
    price = price,
    currency = currency,
    category = ClassifiedCategory.fromValue(category),
    images = images.split(",").filter { it.isNotBlank() },
    sellerId = sellerId,
    sellerName = sellerName,
    location = if (latitude != null && longitude != null) {
        Location(latitude!!, longitude!!, address, null)
    } else null,
    isActive = isActive,
    createdAt = createdAt
)

fun ClassifiedDto.toDomain(): Classified = toEntity().toDomain()

fun List<ClassifiedDto>.toClassifiedEntities(): List<ClassifiedEntity> = map { it.toEntity() }
fun List<ClassifiedEntity>.toClassifiedDomainList(): List<Classified> = map { it.toDomain() }

// ==================== Draft Mappers ====================

fun DraftEntity.toDomain(): Draft = Draft(
    id = id,
    type = DraftType.entries.find { it.value == type } ?: DraftType.NEWS,
    title = title,
    content = content,
    category = category,
    imageUrl = imageUrl,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Draft.toEntity(): DraftEntity = DraftEntity(
    id = id,
    type = type.value,
    title = title,
    content = content,
    category = category,
    imageUrl = imageUrl,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun List<DraftEntity>.toDraftDomainList(): List<Draft> = map { it.toDomain() }
