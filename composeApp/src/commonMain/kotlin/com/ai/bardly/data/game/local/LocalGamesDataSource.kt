package com.ai.bardly.data.game.local

import androidx.paging.PagingSource
import com.ai.bardly.domain.games.model.GameDomainModel
import com.ai.bardly.domain.games.model.local.GameLocalModel
import com.ai.bardly.domain.games.model.local.GamesDao
import com.ai.bardly.domain.games.model.local.toLocalModel

// TODO: interface should be implemented
class LocalGamesDataSource(
    private val gamesDao: GamesDao
) {

    fun getGames(query: String?): PagingSource<Int, GameLocalModel> {
        return gamesDao.getGames()
    }

    suspend fun saveGames(games: List<GameDomainModel>) {
        gamesDao.insert(games.map { it.toLocalModel() })
    }

    suspend fun clearAll() {
        gamesDao.clearAll()
    }
}