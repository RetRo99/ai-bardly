package com.retro99.games.domain

import androidx.paging.PagingData
import com.retro99.base.result.ThrowableResult
import com.retro99.games.domain.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
    suspend fun getRecentlyOpenGames(amount: Int = 6): ThrowableResult<List<GameDomainModel>>
    suspend fun updateGameOpenDate(gameId: Int): ThrowableResult<Unit>
    suspend fun getGamesById(ids: List<Int>): ThrowableResult<List<GameDomainModel>>
    suspend fun addToFavourites(gameId: Int): ThrowableResult<Unit>
    suspend fun removeFromFavourites(gameId: Int): ThrowableResult<Unit>
    fun isMarkedAsFavorite(gameId: Int): Flow<Boolean>
}