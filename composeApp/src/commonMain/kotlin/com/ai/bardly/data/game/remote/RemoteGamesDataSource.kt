package com.ai.bardly.data.game.remote

import androidx.paging.PagingData
import app.cash.paging.PagingConfig
import com.ai.bardly.data.game.GamesDataSource
import com.ai.bardly.data.game.model.GamesListApiResponse
import com.ai.bardly.data.game.model.toDomainModel
import com.ai.bardly.domain.games.model.GameDomainModel
import com.ai.bardly.networking.NetworkClient
import com.ai.bardly.paging.CustomPager
import com.ai.bardly.paging.PagingResult
import kotlinx.coroutines.flow.Flow

class RemoteGamesDataSource(
    private val networkClient: NetworkClient,
) : GamesDataSource {

    override suspend fun getGames(): Flow<PagingData<GameDomainModel>> {
        return CustomPager(
            config = PagingConfig(pageSize = 20),
            initialKey = 0,
            getItems = { key, _ ->
                val response = getGamesUrl(key)
                val prevKey =
                    if (response.currentPage == 0) null else (response.currentPage.dec())
                val nextKey =
                    if (response.currentPage == response.totalPages) null else (response.currentPage.inc())
                PagingResult(response.games.toDomainModel(), prevKey, nextKey)
            }
        ).pagingData
    }

    private suspend fun getGamesUrl(page: Int): GamesListApiResponse {
        return networkClient.get<GamesListApiResponse>(
            path = "games",
            queryBuilder = {
                "page" to page
            }
        ).getOrThrow()
    }
}
