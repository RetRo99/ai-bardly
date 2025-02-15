package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import com.ai.bardly.database.DaoExecutor
import com.ai.bardly.feature.games.data.local.model.GameEntity
import com.ai.bardly.feature.games.data.local.model.GameMetadataEntity
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
    private val daoExecutor: DaoExecutor,
) : GamesLocalDataSource {

    override fun getGames(query: String?): PagingSource<Int, GameEntity> {
        return gamesDao.getGames(query)
    }

    override suspend fun getGamesById(ids: List<Int>): Result<List<GameEntity>> {
        return daoExecutor.executeDaoOperation {
            gamesDao.getGamesById(ids)
        }
    }

    override suspend fun getGame(id: Int): Result<GameEntity> {
        return daoExecutor.executeDaoOperation {
            gamesDao.getGame(id)
        }
    }

    override suspend fun saveGames(games: List<GameEntity>) {
        daoExecutor.executeDaoOperation {
            gamesDao.insert(games)
        }
    }

    override suspend fun getRecentlyOpenGames(amount: Int): Result<List<GameEntity>> {
        return daoExecutor.executeDaoOperation {
            gamesDao.getRecentlyOpenGames(amount)
        }
    }

    override suspend fun clearAll() {
        daoExecutor.executeDaoOperation {
            gamesDao.clearAll()
        }
    }

    override suspend fun updateGameOpenTime(id: Int, openedDateTime: LocalDateTime): Result<Unit> {
        return daoExecutor.executeDaoOperation {
            gamesDao.updateGameOpenTime(GameMetadataEntity(id, openedDateTime))
        }
    }
}