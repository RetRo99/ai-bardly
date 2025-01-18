package com.ai.bardly.data.game.local

import androidx.paging.PagingSource
import com.ai.bardly.domain.games.model.GameDomainModel
import com.ai.bardly.domain.games.model.local.GameLocalModel
import com.ai.bardly.domain.games.model.local.GamesDao
import com.ai.bardly.domain.games.model.local.toLocalModel

class RoomGamesLocalDataSource(
    private val gamesDao: GamesDao
) : GamesLocalDataSource {

    override fun getGames(query: String?): PagingSource<Int, GameLocalModel> {
        return gamesDao.getGames()
    }

    override suspend fun saveGames(games: List<GameDomainModel>) {
        gamesDao.insert(games.map { it.toLocalModel() })
    }

    override suspend fun clearAll() {
        gamesDao.clearAll()
    }
}