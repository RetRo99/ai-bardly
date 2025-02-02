package com.ai.bardly.analytics

sealed class AnalyticsEvent(
    val name: String,
    val params: Map<AnalyticsParam, String>,
) {
    data class ScreenOpen(
        val screenName: String,
        val additionalParams: Map<AnalyticsParam, String> = emptyMap(),
    ) : AnalyticsEvent(
        name = "screen_open",
        params = mapOf(
            AnalyticsParam.ScreenName to screenName,
        ) + additionalParams,
    )

    data class QuestionAsked(
        val gameTitle: String,
    ) : AnalyticsEvent(
        name = "question_asked",
        params = mapOf(
            AnalyticsParam.GameTitle to gameTitle,
        ),
    )

    data object RecentGameClicked : AnalyticsEvent(
        name = "recent_game_clicked",
        params = emptyMap(),
    )

    data object RecentGameChatClicked : AnalyticsEvent(
        name = "recent_game_chat_clicked",
        params = emptyMap(),
    )

    data object RecentChatClicked : AnalyticsEvent(
        name = "recent_chat_clicked",
        params = emptyMap(),
    )
}