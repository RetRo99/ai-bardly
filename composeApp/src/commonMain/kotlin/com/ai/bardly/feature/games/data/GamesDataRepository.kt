package com.ai.bardly.feature.games.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ai.bardly.feature.games.data.local.GamesLocalDataSource
import com.ai.bardly.feature.games.data.local.model.toDomainModel
import com.ai.bardly.feature.games.data.remote.GamesRemoteDataSource
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.domain.model.GameDomainModel
import com.ai.bardly.paging.BardlyRemoteMediator
import kotlinx.coroutines.flow.Flow

class GamesDataRepository(
    private val remoteSource: GamesRemoteDataSource,
    private val localSource: GamesLocalDataSource,
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
                    localSource.saveGames(it)
                },
                clearLocal = {
                    localSource.clearAll()
                }
            ),
            pagingSourceFactory = { localSource.getGames(query) }
        ).flow.toDomainModel()
    }
}