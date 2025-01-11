package com.ai.bardly.data

import androidx.paging.PagingData
import app.cash.paging.PagingConfig
import com.ai.bardly.analytics.Analytics
import com.ai.bardly.paging.CustomPager
import com.ai.bardly.paging.PagingResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

interface GamesApi {
    suspend fun getGames(): Flow<PagingData<GameApiModel>>
}

// TODO(Create network client)
class KtorGamesApi(
    private val client: HttpClient,
    private val analytics: Analytics,
) : GamesApi {
    companion object {
        private const val API_URL = "https://dolphin-app-zeoxd.ondigitalocean.app/games"
    }

    override suspend fun getGames(): Flow<PagingData<GameApiModel>> {
        return CustomPager(
            config = PagingConfig(pageSize = 20),
            initialKey = 0,
            getItems = { key, _ ->
                val response =  try {
                     client.get("$API_URL?page=$key").body<GamesListApiResponse>()
                } catch (e: Exception) {
//                    analytics.logException(e, "games endpoint")
                    client.get("$API_URL?page=${key.inc()}").body<GamesListApiResponse>()
                }
                val prevKey = if (response.currentPage == 0) null else (response.currentPage.dec())
                val nextKey =
                    if (response.currentPage == response.totalPages) null else (response.currentPage.inc())
                PagingResult(response.games, prevKey, nextKey)
            }
        ).pagingData
    }
}
