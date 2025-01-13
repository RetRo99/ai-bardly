package com.ai.bardly.data.game.local

import androidx.paging.PagingData
import com.ai.bardly.data.game.GamesDataSource
import com.ai.bardly.domain.games.model.GameDomainModel
import kotlinx.coroutines.flow.Flow

class LocalGamesDataSource : GamesDataSource {

    override suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>> {
        return throw NotImplementedError()
    }
}
