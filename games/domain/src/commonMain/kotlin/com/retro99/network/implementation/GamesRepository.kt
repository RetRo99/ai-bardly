package com.retro99.network.implementation

import androidx.paging.PagingData
import com.retro99.network.implementation.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
    suspend fun getRecentlyOpenGames(amount: Int = 6): Result<List<GameDomainModel>>
    suspend fun updateGameOpenDate(gameId: Int): Result<Unit>
    suspend fun getGamesById(ids: List<Int>): Result<List<GameDomainModel>>
}