package com.retro99.games.data.local

import androidx.paging.PagingSource
import com.retro99.database.api.DatabaseExecutor
import com.retro99.games.data.local.model.GameEntity
import com.retro99.games.data.local.model.GameMetadataEntity
import kotlinx.datetime.LocalDateTime
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class RoomGamesLocalDataSource(
    private val gamesDao: GamesDao,
    private val daoExecutor: DatabaseExecutor,
) : GamesLocalDataSource {

    override fun getGames(query: String?): PagingSource<Int, GameEntity> {
        return gamesDao.getGames(query)
    }

    override suspend fun getGamesById(ids: List<Int>): Result<List<GameEntity>> {
        return daoExecutor.executeDatabaseOperation {
            gamesDao.getGamesById(ids)
        }
    }

    override suspend fun getGame(id: Int): Result<GameEntity> {
        return daoExecutor.executeDatabaseOperation {
            gamesDao.getGame(id)
        }
    }

    override suspend fun saveGames(games: List<GameEntity>) {
        daoExecutor.executeDatabaseOperation {
            gamesDao.insert(games)
        }
    }

    override suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameEntity>> {
        return daoExecutor.executeDatabaseOperation {
            gamesDao.getRecentlyOpenGames(amount)
        }
    }

    override suspend fun clearAll() {
        daoExecutor.executeDatabaseOperation {
            gamesDao.clearAll()
        }
    }

    override suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime): Result<Unit> {
        return daoExecutor.executeDatabaseOperation {
            gamesDao.updateGameOpenTime(GameMetadataEntity(id, openedDateTime))
        }
    }
}