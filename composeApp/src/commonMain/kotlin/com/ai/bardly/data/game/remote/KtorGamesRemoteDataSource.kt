package com.ai.bardly.data.game.remote

import androidx.paging.PagingSource
import com.ai.bardly.data.game.model.GamesListApiResponse
import com.ai.bardly.data.game.model.toDomainModel
import com.ai.bardly.domain.games.model.local.GameLocalModel
import com.ai.bardly.domain.games.model.local.toLocalModel
import com.ai.bardly.networking.NetworkClient
import com.ai.bardly.paging.BardlyPagingSource
import com.ai.bardly.paging.PagingResult

class KtorGamesRemoteDataSource(
    private val networkClient: NetworkClient,
) : GamesRemoteDataSource {

    override suspend fun getGames(query: String?): PagingSource<Int, GameLocalModel> {
        return BardlyPagingSource(
            initialKey = 1,
            getItems = { key, _ ->
                val response = getGamesUrl(key, query)
                val prevKey =
                    if (response.currentPage == 0) null else (response.currentPage.dec())
                val nextKey =
                    if (response.currentPage == response.totalPages) null else (response.currentPage.inc())
                PagingResult(
                    response.games.toDomainModel().map { it.toLocalModel() },
                    prevKey,
                    nextKey
                )
            }
        )
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
