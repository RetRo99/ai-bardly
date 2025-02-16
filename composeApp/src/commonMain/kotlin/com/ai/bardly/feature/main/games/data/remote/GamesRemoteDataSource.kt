package com.ai.bardly.feature.main.games.data.remote

import androidx.paging.PagingSource
import com.ai.bardly.feature.main.games.data.remote.model.GameDto

interface GamesRemoteDataSource {
    suspend fun getGames(query: String?): PagingSource<Int, GameDto>
}