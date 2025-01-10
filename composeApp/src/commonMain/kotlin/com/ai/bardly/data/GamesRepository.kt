package com.ai.bardly.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class GamesRepository(
    private val gamesApi: GamesApi,
) {
    suspend fun getObjects(): Flow<PagingData<GameApiModel>> = gamesApi.getGames()
}
