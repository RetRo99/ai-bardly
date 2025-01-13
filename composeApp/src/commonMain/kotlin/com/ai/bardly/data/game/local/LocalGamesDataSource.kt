package com.ai.bardly.data.game.local

import androidx.paging.PagingData
import com.ai.bardly.data.game.GamesDataSource
import com.ai.bardly.domain.games.model.GameDomainModel
import com.ai.bardly.domain.games.model.local.GamesDao
import kotlinx.coroutines.flow.Flow

class LocalGamesDataSource(
    private val gamesDao: GamesDao
) : GamesDataSource {

    override suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>> {
        throw NotImplementedError()
    }

    override suspend fun saveGames(games: List<GameDomainModel>) {
        throw NotImplementedError()
    }
}