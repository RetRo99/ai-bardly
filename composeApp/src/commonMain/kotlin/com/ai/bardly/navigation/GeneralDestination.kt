package com.ai.bardly.navigation

import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.feature.games.ui.model.GameUiModel
import kotlinx.serialization.Serializable

@Serializable
sealed class GeneralDestination : ScreenViewAnalytics() {

    @Serializable
    data class GameDetail(
        val game: GameUiModel,
    ) : GeneralDestination() {

        override fun logScreenOpen() {
            val params = mapOf(
                "screen_name" to "game_details",
                "game_title" to game.title
            )
            analytics.log(AnalyticsEvent.SCREEN_OPEN, params)
        }
    }

    @Serializable
    data class ChatDetail(
        val gameTitle: String,
        val gameId: Int,
    ) : GeneralDestination() {

        override fun logScreenOpen() {
            val params = mapOf(
                "screen_name" to "chat",
                "game_title" to gameTitle
            )
            analytics.log(AnalyticsEvent.SCREEN_OPEN, params)
        }
    }

    data object Back : GeneralDestination() {
        override fun logScreenOpen() {
            // Not logging
        }
    }
}