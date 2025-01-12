package com.ai.bardly.data.game

import androidx.paging.PagingData
import app.cash.paging.PagingConfig
import com.ai.bardly.data.game.model.GameApiModel
import com.ai.bardly.data.game.model.GamesListApiResponse
import com.ai.bardly.networking.NetworkClient
import com.ai.bardly.paging.CustomPager
import com.ai.bardly.paging.PagingResult
import kotlinx.coroutines.flow.Flow

interface GamesApi {
    suspend fun getGames(): Flow<PagingData<GameApiModel>>
}

// TODO(Create network client)
class KtorGamesApi(
    private val networkClient: NetworkClient,
) : GamesApi {

    override suspend fun getGames(): Flow<PagingData<GameApiModel>> {
        return CustomPager(
            config = PagingConfig(pageSize = 20),
            initialKey = 0,
            getItems = { key, _ ->
                val response = getGamesUrl(key)
                val prevKey =
                    if (response.currentPage == 0) null else (response.currentPage.dec())
                val nextKey =
                    if (response.currentPage == response.totalPages) null else (response.currentPage.inc())
                PagingResult(response.games, prevKey, nextKey)
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
