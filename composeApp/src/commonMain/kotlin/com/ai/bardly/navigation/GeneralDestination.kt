package com.ai.bardly.navigation

import com.ai.bardly.GameUiModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface GeneralDestination {

    @Serializable
    data class GameDetail(
        val game: GameUiModel,
    ) : GeneralDestination
}