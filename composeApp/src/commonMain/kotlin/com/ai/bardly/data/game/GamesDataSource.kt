package com.ai.bardly.data.game

import androidx.paging.PagingData
import com.ai.bardly.data.game.model.GameApiModel
import kotlinx.coroutines.flow.Flow

interface GamesDataSource {
    suspend fun getGames(): Flow<PagingData<GameApiModel>>
}