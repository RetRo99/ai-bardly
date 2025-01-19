package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import com.ai.bardly.feature.games.data.local.model.GameLocalModel
import com.ai.bardly.feature.games.domain.model.GameDomainModel

interface GamesLocalDataSource {
    fun getGames(query: String?): PagingSource<Int, GameLocalModel>

    suspend fun saveGames(games: List<GameDomainModel>)

    suspend fun clearAll()
}