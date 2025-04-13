package com.retro99.shelfs.data.local

import com.retro99.base.result.AppResult
import com.retro99.database.api.shelfs.ShelfEntity
import com.retro99.database.api.shelfs.ShelfsDatabase
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

    override suspend fun getShelf(id: Int): AppResult<ShelfEntity> {
        return shelfsDatabase.getShelf(id)
    }

    override suspend fun getShelfs(): AppResult<List<ShelfEntity>> {
        return shelfsDatabase.getShelfs()
    }

}