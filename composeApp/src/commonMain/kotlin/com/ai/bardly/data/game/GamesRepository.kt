package com.ai.bardly.data.game

import androidx.paging.PagingData
import com.ai.bardly.data.game.model.GameApiModel
import kotlinx.coroutines.flow.Flow

// TODO extract interface
class GamesRepository(
    private val gamesApi: GamesDataSource,
) {
    suspend fun getObjects(): Flow<PagingData<GameApiModel>> = gamesApi.getGames()
}
