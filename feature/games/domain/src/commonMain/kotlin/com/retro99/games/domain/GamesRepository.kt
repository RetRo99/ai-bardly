package com.retro99.games.domain

import androidx.paging.PagingData
import com.retro99.base.result.AppResult
import com.retro99.base.result.CompletableResult
import com.retro99.games.domain.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
    suspend fun getRecentlyOpenGames(amount: Int = 6): AppResult<List<GameDomainModel>>
    suspend fun updateGameOpenDate(gameId: String): CompletableResult
    suspend fun getGamesById(ids: List<String>): AppResult<List<GameDomainModel>>
    suspend fun addToFavourites(gameId: String): CompletableResult
    suspend fun removeFromFavourites(gameId: String): CompletableResult
    fun isMarkedAsFavorite(gameId: String): Flow<Boolean>
}
