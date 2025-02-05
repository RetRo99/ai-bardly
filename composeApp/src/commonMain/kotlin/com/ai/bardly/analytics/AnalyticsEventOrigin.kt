package com.ai.bardly.analytics

sealed class AnalyticsEventOrigin(val analyticKey: String) {
    data object Home : AnalyticsEventOrigin("home")
    data object Chat : AnalyticsEventOrigin("chat_details")
    data object GameDetails : AnalyticsEventOrigin("game_details")
    data object GameList : AnalyticsEventOrigin("game_list")
    data object GameSearch : AnalyticsEventOrigin("game_search")
    data object RecentChats : AnalyticsEventOrigin("recent_chats")
}
