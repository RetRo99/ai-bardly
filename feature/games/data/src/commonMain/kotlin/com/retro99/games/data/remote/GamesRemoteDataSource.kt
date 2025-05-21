package com.retro99.games.data.remote

import androidx.paging.PagingSource
import com.retro99.base.result.AppResult
import com.retro99.base.result.CompletableResult
import com.retro99.games.data.remote.model.GameDto

interface GamesRemoteDataSource {
    suspend fun getGames(query: String?): PagingSource<Int, GameDto>
    suspend fun addToFavourites(gameId: String): CompletableResult
    suspend fun removeFromFavourites(gameId: String): CompletableResult
    suspend fun isMarkedAsFavorite(gameId: String): AppResult<Boolean>
}
