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

    override suspend fun getGames(query: String?): Flow<PagingData<GameDomainModel>> {
        return CustomPager(
            config = PagingConfig(pageSize = 20),
            initialKey = 0,
            getItems = { key, _ ->
                val response = getGamesUrl(key, query)
                val prevKey =
                    if (response.currentPage == 0) null else (response.currentPage.dec())
                val nextKey =
                    if (response.currentPage == response.totalPages) null else (response.currentPage.inc())
                PagingResult(response.games.toDomainModel(), prevKey, nextKey)
            }
        ).pagingData
    }

    override suspend fun saveGames(games: List<GameDomainModel>) {
        throw NotImplementedError()
    }

    private suspend fun getGamesUrl(page: Int, query: String?): GamesListApiResponse {
        return networkClient.get<GamesListApiResponse>(
            path = "games",
            queryBuilder = {
                "page" to page
                "search" to query
            }
        ).getOrThrow()
    }
}
