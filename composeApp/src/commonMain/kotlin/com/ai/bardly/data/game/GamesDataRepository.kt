package com.ai.bardly.data.game

import androidx.paging.PagingData
import com.ai.bardly.domain.games.GamesRepository
import com.ai.bardly.domain.games.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

class GamesDataRepository(
    private val remoteSource: GamesDataSource,
    private val localSource: GamesDataSource,
) : GamesRepository {
    override suspend fun getGames(
        query: String?,
    ): Flow<PagingData<GameDomainModel>> = remoteSource
        .getGames(query)
}
