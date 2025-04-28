package com.retro99.shelfs.domain

import com.retro99.base.result.AppResult
import com.retro99.shelfs.domain.model.ShelfDomainModel
import kotlinx.coroutines.flow.Flow

interface ShelfsRepository {
    suspend fun getShelf(id: String): AppResult<ShelfDomainModel>
    suspend fun getShelfs(): Flow<AppResult<List<ShelfDomainModel>>>
}
