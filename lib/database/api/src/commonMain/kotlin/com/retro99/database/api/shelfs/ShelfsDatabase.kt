package com.retro99.database.api.shelfs

import com.retro99.base.result.AppResult

interface ShelfsDatabase {

    suspend fun insert(item: ShelfEntity): AppResult<Unit>

    suspend fun insert(items: List<ShelfEntity>): AppResult<Unit>

    suspend fun getShelf(id: String): AppResult<ShelfEntity>

    suspend fun getShelfs(): AppResult<List<ShelfEntity>>

    suspend fun deleteShelf(id: String): AppResult<Unit>
}
