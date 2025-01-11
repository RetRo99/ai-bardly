package com.ai.bardly.navigation

import com.ai.bardly.GameUiModel
import com.ai.bardly.analytics.AnalyticsEvent
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

    data object Back : GeneralDestination() {
        override fun logScreenOpen() {
            // Not logging
        }
    }
}