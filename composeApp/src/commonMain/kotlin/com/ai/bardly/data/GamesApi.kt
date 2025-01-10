package com.ai.bardly.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.CancellationException

interface GamesApi {
    suspend fun getGames(): List<GameApiModel>
}

class KtorGamesApi(private val client: HttpClient) : GamesApi {
    companion object {
        private const val API_URL =
            "http://localhost:3000/games"
    }

    override suspend fun getGames(): List<GameApiModel> {
        return try {
            client.get(API_URL).body()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()

            emptyList()
        }
    }
}
