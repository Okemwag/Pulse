package com.okemwag.data.repository

import com.okemwag.data.mapper.*
import com.okemwag.database.dao.DraftDao
import com.okemwag.domain.model.Draft
import com.okemwag.domain.model.DraftType
import com.okemwag.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DraftRepositoryImpl @Inject constructor(
    private val draftDao: DraftDao
) : DraftRepository {

    override fun getAllDrafts(): Flow<List<Draft>> {
        return draftDao.getAllDrafts().map { it.toDraftDomainList() }
    }

    override fun getDraftsByType(type: DraftType): Flow<List<Draft>> {
        return draftDao.getDraftsByType(type.value).map { it.toDraftDomainList() }
    }

    override suspend fun getDraftById(id: Long): Result<Draft> {
        return try {
            val draft = draftDao.getDraftById(id)
            if (draft != null) {
                Result.success(draft.toDomain())
            } else {
                Result.failure(Exception("Draft not found"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching draft")
            Result.failure(e)
        }
    }

    override suspend fun saveDraft(draft: Draft): Result<Long> {
        return try {
            val id = draftDao.insertDraft(draft.toEntity())
            Result.success(id)
        } catch (e: Exception) {
            Timber.e(e, "Error saving draft")
            Result.failure(e)
        }
    }

    override suspend fun deleteDraft(id: Long): Result<Unit> {
        return try {
            draftDao.deleteDraftById(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e(e, "Error deleting draft")
            Result.failure(e)
        }
    }
}
