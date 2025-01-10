package com.ai.bardly.data

import kotlinx.serialization.Serializable

@Serializable
data class GamesListApiResponse(
    val games: List<GameApiModel>
)
