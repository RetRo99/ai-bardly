package com.retro99.games.data.remote

import androidx.paging.PagingSource
import com.retro99.games.data.remote.model.GameDto

interface GamesRemoteDataSource {
    suspend fun getGames(query: String?): PagingSource<Int, GameDto>
    suspend fun markAsFavourite(gameId: Int): Result<Unit>
}