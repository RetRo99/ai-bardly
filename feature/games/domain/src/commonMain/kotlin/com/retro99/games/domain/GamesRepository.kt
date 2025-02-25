package com.retro99.games.domain

import androidx.paging.PagingData
import com.retro99.games.domain.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
    suspend fun getRecentlyOpenGames(amount: Int = 6): Result<List<GameDomainModel>>
    suspend fun updateGameOpenDate(gameId: Int): Result<Unit>
    suspend fun getGamesById(ids: List<Int>): Result<List<GameDomainModel>>
    suspend fun addToFavourites(gameId: Int): Result<Unit>
    suspend fun removeFromFavourites(gameId: Int): Result<Unit>
    fun isMarkedAsFavorite(gameId: Int): Flow<Boolean>
}