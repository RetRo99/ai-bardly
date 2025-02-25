package com.retro99.database.implementation.dao.games

import androidx.paging.PagingSource
import com.retro99.database.api.games.GameEntity
import com.retro99.database.api.games.GamesDatabase
import com.retro99.database.implementation.DatabaseExecutor
import kotlinx.datetime.LocalDateTime
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomGamesDatabase(
    private val dao: GamesDao,
    private val daoExecutor: DatabaseExecutor,
) : GamesDatabase {
    override suspend fun insert(item: GameEntity): Result<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.insert(item.toRoomEntity())
        }
    }

    override suspend fun insert(items: List<GameEntity>): Result<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.insert(items.toRoomEntity())
        }
    }

    override suspend fun getGame(id: Int): Result<GameEntity> {
        return daoExecutor.executeDatabaseOperation {
            dao.getGame(id)
        }
    }

    override suspend fun getGamesById(ids: List<Int>): Result<List<GameEntity>> {
        return daoExecutor.executeDatabaseOperation {
            dao.getGamesById(ids)
        }
    }

    override suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameEntity>> {
        return daoExecutor.executeDatabaseOperation {
            dao.getRecentlyOpenGames(amount)
        }
    }

    override suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime?): Result<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.updateGameOpenTime(id, openedDateTime)
        }
    }

    override suspend fun clearAll(): Result<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.clearAll()
        }
    }

    override fun getGames(query: String?): PagingSource<Int, GameEntity> {
        @Suppress("UNCHECKED_CAST")
        return dao.getGames(query) as PagingSource<Int, GameEntity>
    }

    override suspend fun markAsFavourite(gameId: Int): Result<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.markAsFavourite(gameId)
        }
    }

    override suspend fun isMarkedAsFavorite(gameId: Int): Result<Boolean> {
        return daoExecutor.executeDatabaseOperation {
            dao.isMarkedAsFavorite(gameId) == true
        }
    }
}