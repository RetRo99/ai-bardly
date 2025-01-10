package com.ai.bardly.data

import androidx.paging.PagingData
import app.cash.paging.PagingConfig
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
class KtorGamesApi(private val client: HttpClient) : GamesApi {
    companion object {
        private const val API_URL = "http://10.0.2.2:3000/games"
    }

    override suspend fun getGames(): Flow<PagingData<GameApiModel>> {
        return CustomPager(
            config = PagingConfig(pageSize = 20),
            initialKey = 0,
            getItems = { key, _ ->
                val response = client.get("$API_URL?page=$key").body<GamesListApiResponse>()
                val prevKey = if (key == 0) null else (key.dec())
                val nextKey =
                    if (key == response.totalPages) null else (key.inc())
                PagingResult(response.games, prevKey, nextKey)
            }
        ).pagingData
    }
}
