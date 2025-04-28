package com.retro99.shelfs.data.local

import com.retro99.base.result.AppResult
import com.retro99.database.api.shelfs.ShelfEntity

interface ShelfsLocalDataSource {
    suspend fun save(item: ShelfEntity): AppResult<Unit>

    suspend fun save(items: List<ShelfEntity>): AppResult<Unit>

    suspend fun getShelf(id: String): AppResult<ShelfEntity>

    suspend fun getShelfs(): AppResult<List<ShelfEntity>>
}
