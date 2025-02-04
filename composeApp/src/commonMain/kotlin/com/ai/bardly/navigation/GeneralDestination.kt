package com.ai.bardly.navigation

import com.ai.bardly.feature.games.ui.model.GameUiModel
import kotlinx.serialization.Serializable

@Serializable
sealed class GeneralDestination {

    @Serializable
    data class GameDetail(
        val game: GameUiModel,
    ) : GeneralDestination()

    @Serializable
    data class Chat(
        val gameTitle: String,
        val gameId: Int,
    ) : GeneralDestination()

    @Serializable
    data object Back : GeneralDestination()
}