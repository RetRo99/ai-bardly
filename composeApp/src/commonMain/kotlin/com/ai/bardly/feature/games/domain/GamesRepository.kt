package com.ai.bardly.feature.games.domain

import androidx.paging.PagingData
import com.ai.bardly.feature.games.domain.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
}