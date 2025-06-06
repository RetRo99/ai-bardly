package com.retro99.shelfs.data.local

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.retro99.base.result.AppResult
import com.retro99.database.api.shelfs.ShelfEntity
import com.retro99.database.api.shelfs.ShelfsDatabase
import com.retro99.shelfs.data.local.model.ShelfLocalModel
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomShelfsLocalDataSource(
    private val shelfsDatabase: ShelfsDatabase,
) : ShelfsLocalDataSource {
    override suspend fun save(item: ShelfEntity): AppResult<Unit> {
        return shelfsDatabase.insert(item)
    }

    override suspend fun save(items: List<ShelfEntity>): AppResult<Unit> {
        return shelfsDatabase.insert(items)
    }

    override suspend fun getShelf(id: String): AppResult<ShelfEntity> {
        return shelfsDatabase.getShelf(id)
    }

    override suspend fun getShelfs(): AppResult<List<ShelfEntity>> {
        return shelfsDatabase.getShelfs()
    }

    override suspend fun deleteShelf(id: String): AppResult<Unit> {
        return shelfsDatabase.deleteShelf(id)
    }
}
