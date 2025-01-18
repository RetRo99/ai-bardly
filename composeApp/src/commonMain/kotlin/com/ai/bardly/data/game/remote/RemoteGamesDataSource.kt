package com.ai.bardly.data.game.remote

import androidx.paging.PagingSource
import com.ai.bardly.domain.games.model.local.GameLocalModel

interface RemoteGamesDataSource {
    suspend fun getGames(query: String?): PagingSource<Int, GameLocalModel>
}