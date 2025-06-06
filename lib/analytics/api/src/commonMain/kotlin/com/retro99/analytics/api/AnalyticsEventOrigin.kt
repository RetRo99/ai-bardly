package com.retro99.analytics.api

sealed class AnalyticsEventOrigin(val analyticKey: String) {
    data object Home : AnalyticsEventOrigin("home")
    data object Chat : AnalyticsEventOrigin("chat_details")
    data object GameDetails : AnalyticsEventOrigin("game_details")
    data object GameList : AnalyticsEventOrigin("game_list")
    data object GameSearch : AnalyticsEventOrigin("game_search")
    data object RecentChats : AnalyticsEventOrigin("recent_chats")
    data object SignIn : AnalyticsEventOrigin("sign_in")
    data object SignUp : AnalyticsEventOrigin("sign_in")
    data object ShelfList : AnalyticsEventOrigin("shelf_list")
    data object ShelfDetails : AnalyticsEventOrigin("shelf_details")
}
