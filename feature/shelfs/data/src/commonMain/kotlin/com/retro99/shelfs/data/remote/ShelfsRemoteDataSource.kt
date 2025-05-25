package com.retro99.shelfs.data.remote

import com.retro99.base.result.AppResult
import com.retro99.base.result.CompletableResult
import com.retro99.shelfs.data.remote.model.CreateShelfDto
import com.retro99.shelfs.data.remote.model.ShelfDto
import com.retro99.shelfs.domain.model.CreateShelfDomainModel

interface ShelfsRemoteDataSource {
    suspend fun getShelfs(): AppResult<List<ShelfDto>>
    suspend fun getShelf(id: String): AppResult<ShelfDto>
    suspend fun addGameToShelf(shelfId: String, gameId: String): CompletableResult
    suspend fun createShelf(item: CreateShelfDto): AppResult<ShelfDto>
}
