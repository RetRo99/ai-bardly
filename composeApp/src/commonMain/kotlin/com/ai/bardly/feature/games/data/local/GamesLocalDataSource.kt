package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import com.ai.bardly.feature.games.data.local.model.GameEntity

interface GamesLocalDataSource {
    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun saveGames(games: List<GameEntity>)

    suspend fun clearAll()
}