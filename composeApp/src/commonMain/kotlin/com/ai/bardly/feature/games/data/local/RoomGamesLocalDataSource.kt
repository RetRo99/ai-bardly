package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import com.ai.bardly.database.DaoExecutor
import com.ai.bardly.feature.games.data.local.model.GameLocalModel
import com.ai.bardly.feature.games.data.local.model.toLocalModel
import com.ai.bardly.feature.games.domain.model.GameDomainModel

class RoomGamesLocalDataSource(
    private val gamesDao: GamesDao,
    private val daoExecutor: DaoExecutor,
) : GamesLocalDataSource {

    override fun getGames(query: String?): PagingSource<Int, GameLocalModel> {
        return gamesDao.getGames()
    }

    override suspend fun saveGames(games: List<GameDomainModel>) {
        daoExecutor.executeDaoOperation {
            gamesDao.insert(games.map { it.toLocalModel() })
        }
    }

    override suspend fun clearAll() {
        daoExecutor.executeDaoOperation {
            gamesDao.clearAll()
        }
    }
}