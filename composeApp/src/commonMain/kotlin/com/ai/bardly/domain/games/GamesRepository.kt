package com.ai.bardly.domain.games

import androidx.paging.PagingData
import com.ai.bardly.domain.games.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
}