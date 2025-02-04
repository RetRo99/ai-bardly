package com.ai.bardly.analytics

sealed class AnalyticsEventParam(val analyticKey: String) {
    data object GameTitle : AnalyticsEventParam("game_title")
    data object ScreenName : AnalyticsEventParam("screen_name")
}
