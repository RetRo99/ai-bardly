package com.retro99.games.domain

import androidx.paging.PagingData
import com.retro99.base.result.AppResult
import com.retro99.games.domain.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
    suspend fun getRecentlyOpenGames(amount: Int = 6): AppResult<List<GameDomainModel>>
    suspend fun updateGameOpenDate(gameId: Int): AppResult<Unit>
    suspend fun getGamesById(ids: List<Int>): AppResult<List<GameDomainModel>>
    suspend fun addToFavourites(gameId: Int): AppResult<Unit>
    suspend fun removeFromFavourites(gameId: Int): AppResult<Unit>
    fun isMarkedAsFavorite(gameId: Int): Flow<Boolean>
}