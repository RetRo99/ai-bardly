package com.retro99.shelfs.domain

import com.retro99.base.repository.BaseRepository
import com.retro99.base.result.AppResult
import com.retro99.base.result.CompletableResult
import com.retro99.shelfs.domain.model.CreateShelfDomainModel
import com.retro99.shelfs.domain.model.ShelfDomainModel
import kotlinx.coroutines.flow.Flow

interface ShelfsRepository: BaseRepository {
    suspend fun getShelf(id: String): Flow<AppResult<ShelfDomainModel>>
    suspend fun getShelfs(): Flow<AppResult<List<ShelfDomainModel>>>
    suspend fun addGameToShelf(shelfId: String, gameId: String): CompletableResult
    suspend fun deleteGameFromShelf(shelfId: String, gameId: String): CompletableResult
    suspend fun createShelf(item: CreateShelfDomainModel): AppResult<ShelfDomainModel>
    suspend fun deleteShelf(id: String): CompletableResult
}
