package com.retro99.games.data.remote

import androidx.paging.PagingSource
import com.retro99.base.result.AppResult
import com.retro99.games.data.remote.model.GameDto

interface GamesRemoteDataSource {
    suspend fun getGames(query: String?): PagingSource<Int, GameDto>
    suspend fun addToFavourites(gameId: Int): AppResult<Unit>
    suspend fun removeFromFavourites(gameId: Int): AppResult<Unit>
    suspend fun isMarkedAsFavorite(gameId: Int): AppResult<Boolean>
}