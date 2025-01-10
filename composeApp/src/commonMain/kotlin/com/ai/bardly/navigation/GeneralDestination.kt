package com.ai.bardly.navigation

import com.ai.bardly.GameUiModel
import kotlinx.serialization.Serializable

@Serializable
data class GameDetail(
    val game: GameUiModel,
)