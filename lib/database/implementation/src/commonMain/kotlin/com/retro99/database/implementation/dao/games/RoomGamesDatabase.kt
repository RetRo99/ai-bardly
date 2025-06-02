package com.retro99.database.implementation.dao.games

import androidx.paging.PagingSource
import com.retro99.base.result.AppResult
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
    override suspend fun insert(item: GameEntity): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.insert(item.toRoomEntity())
        }
    }

    override suspend fun insert(items: List<GameEntity>): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.insert(items.toRoomEntity())
        }
    }

    override suspend fun getGame(id: String): AppResult<GameEntity> {
        return daoExecutor.executeDatabaseOperation {
            dao.getGame(id)
        }
    }

    override suspend fun getGamesById(ids: List<String>): AppResult<List<GameEntity>> {
        return daoExecutor.executeDatabaseOperation {
            dao.getGamesById(ids)
        }
    }

    override suspend fun getRecentlyOpenGames(amount: Int): AppResult<List<GameEntity>> {
        return daoExecutor.executeDatabaseOperation {
            dao.getRecentlyOpenGames(amount)
        }
    }

    override suspend fun updateGameOpenTime(
        id: String,
        openedDateTime: LocalDateTime?
    ): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.updateGameOpenTime(id, openedDateTime)
        }
    }

    override suspend fun clearAll(): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.clearAll()
        }
    }

    override fun getGames(query: String?): PagingSource<Int, GameEntity> {
        @Suppress("UNCHECKED_CAST")
        return dao.getGames(query) as PagingSource<Int, GameEntity>
    }

    override suspend fun addToFavourites(gameId: String): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.updateIsFavorite(gameId, true)
        }
    }

    override suspend fun removeFromFavourites(gameId: String): AppResult<Unit> {
        return daoExecutor.executeDatabaseOperation {
            dao.updateIsFavorite(gameId, false)
        }
    }

    override suspend fun isMarkedAsFavorite(gameId: String): AppResult<Boolean> {
        return daoExecutor.executeDatabaseOperation {
            dao.isMarkedAsFavorite(gameId) == true
        }
    }
}
