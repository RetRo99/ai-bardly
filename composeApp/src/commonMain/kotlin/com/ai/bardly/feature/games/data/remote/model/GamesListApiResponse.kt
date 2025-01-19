package com.ai.bardly.feature.games.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GamesListApiResponse(
    val games: List<GameApiModel>,
    val totalPages: Int,
    val currentPage: Int,
)
