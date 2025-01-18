package com.ai.bardly.data.game

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ai.bardly.data.game.local.LocalGamesDataSource
import com.ai.bardly.domain.games.GamesRepository
import com.ai.bardly.domain.games.model.GameDomainModel
import com.ai.bardly.domain.games.model.local.toDomainModel
import com.ai.bardly.paging.BardlyRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GamesDataRepository(
    private val remoteSource: GamesDataSource,
    private val localSource: LocalGamesDataSource,
) : GamesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>> {
        val remotePagingSource = remoteSource.getGames(query)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            remoteMediator = BardlyRemoteMediator(
                remoteSource = remotePagingSource,
                localSource = localSource.getGames(query),
                saveToLocal = {
                    localSource.saveGames(it.map { it.toDomainModel() })
                },
                clearLocal = {
                    localSource.clearAll()
                }
            ),
            pagingSourceFactory = { localSource.getGames(query) }
        ).flow.map { it.map { it.toDomainModel() } }
    }
}