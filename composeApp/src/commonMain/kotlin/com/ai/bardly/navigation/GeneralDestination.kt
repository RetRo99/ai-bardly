package com.ai.bardly.navigation

import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsParam
import com.ai.bardly.feature.games.ui.model.GameUiModel
import kotlinx.serialization.Serializable

@Serializable
sealed class GeneralDestination : ScreenViewAnalytics() {

    @Serializable
    data class GameDetail(
        val game: GameUiModel,
    ) : GeneralDestination() {

        override fun logScreenOpen() {
            analytics.log(
                AnalyticsEvent.ScreenOpen(
                    screenName = "game_details",
                    AnalyticsParam.GameTitle to game.title,
                )
            )
        }
    }

    @Serializable
    data class ChatDetails(
        val gameTitle: String,
        val gameId: Int,
    ) : GeneralDestination() {

        override fun logScreenOpen() {
            analytics.log(
                AnalyticsEvent.ScreenOpen(
                    screenName = "chat_details",
                    AnalyticsParam.GameTitle to gameTitle,
                )
            )
        }
    }

    data object Back : GeneralDestination() {
        override fun logScreenOpen() {
            // Not logging
        }
    }
}