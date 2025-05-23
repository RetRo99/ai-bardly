package com.retro99.shelfs.data.remote

import com.retro99.base.result.AppResult
import com.retro99.shelfs.data.remote.model.ShelfDto

interface ShelfsRemoteDataSource {
    suspend fun getShelfs(): AppResult<List<ShelfDto>>
    suspend fun getShelf(id: String): AppResult<ShelfDto>
}
