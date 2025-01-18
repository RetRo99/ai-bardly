package com.ai.bardly.data.game

import androidx.paging.PagingSource
import com.ai.bardly.domain.games.model.GameDomainModel
import com.ai.bardly.domain.games.model.local.GameLocalModel

interface GamesDataSource {
    suspend fun getGames(query: String?): PagingSource<Int, GameLocalModel>
    suspend fun saveGames(games: List<GameDomainModel>)
    suspend fun clearAll()
}