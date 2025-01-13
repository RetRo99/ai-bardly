package com.ai.bardly.data.game

import androidx.paging.PagingData
import com.ai.bardly.domain.games.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

interface GamesDataSource {
    suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>>
    suspend fun saveGames(games: List<GameDomainModel>)
}