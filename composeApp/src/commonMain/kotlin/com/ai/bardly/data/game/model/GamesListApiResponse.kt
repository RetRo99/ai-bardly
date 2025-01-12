package com.ai.bardly.data.game.model

import kotlinx.serialization.Serializable

@Serializable
data class GamesListApiResponse(
    val games: List<GameApiModel>,
    val totalPages: Int,
    val currentPage: Int,
)
