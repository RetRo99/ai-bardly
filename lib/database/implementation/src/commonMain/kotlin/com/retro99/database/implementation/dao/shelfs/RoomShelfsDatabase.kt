package com.retro99.database.implementation.dao.shelfs

import com.retro99.base.result.AppResult
import com.retro99.database.api.shelfs.ShelfEntity
import com.retro99.database.api.shelfs.ShelfsDatabase
import com.retro99.database.implementation.DatabaseExecutor
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomShelfsDatabase(
    private val dao: ShelfsDao,
    private val daoExecutor: DatabaseExecutor,
) : ShelfsDatabase {

    override suspend fun insert(item: ShelfEntity): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.insert(item.toRoomEntity())
        }
    }

    override suspend fun insert(items: List<ShelfEntity>): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.insert(items.toRoomEntity())
        }
    }

    override suspend fun getShelf(id: String): AppResult<ShelfEntity> {
        return daoExecutor.executeDatabaseOperation {
            dao.getShelf(id)
        }
    }

    override suspend fun getShelfs(): AppResult<List<ShelfEntity>> {
        return daoExecutor.executeDatabaseOperation {
            dao.getShelfs()
        }
    }

    override suspend fun deleteShelf(id: String): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.deleteShelf(id)
        }
    }
}
