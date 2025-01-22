package com.ai.bardly.feature.games.domain

import androidx.paging.PagingData
import com.ai.bardly.feature.games.domain.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
    suspend fun getRecentlyOpenGames(amount: Int = 6): Result<List<GameDomainModel>>
    suspend fun updateGameOpenDate(gameId: Int): Result<Unit>
}