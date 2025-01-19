package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import com.ai.bardly.database.DaoExecutor
import com.ai.bardly.feature.games.data.local.model.GameEntity

class RoomGamesLocalDataSource(
    private val gamesDao: GamesDao,
    private val daoExecutor: DaoExecutor,
) : GamesLocalDataSource {

    override fun getGames(query: String?): PagingSource<Int, GameEntity> {
        return gamesDao.getGames()
    }

    override suspend fun saveGames(games: List<GameEntity>) {
        daoExecutor.executeDaoOperation {
            gamesDao.insert(games)
        }
    }

    override suspend fun clearAll() {
        daoExecutor.executeDaoOperation {
            gamesDao.clearAll()
        }
    }
}