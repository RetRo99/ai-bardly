package com.ai.bardly.feature.games.data.local

import androidx.paging.PagingSource
import com.ai.bardly.feature.games.data.local.model.GameEntity
import com.ai.bardly.feature.games.domain.model.GameDomainModel

interface GamesLocalDataSource {
    fun getGames(query: String?): PagingSource<Int, GameEntity>

    suspend fun saveGames(games: List<GameDomainModel>)

    suspend fun clearAll()
}