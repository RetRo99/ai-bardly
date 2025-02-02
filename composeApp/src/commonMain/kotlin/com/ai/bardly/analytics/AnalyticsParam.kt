package com.ai.bardly.analytics

sealed class AnalyticsParam(val analyticKey: String) {
    data object GameTitle : AnalyticsParam("game_title")
    data object ScreenName : AnalyticsParam("screen_name")
}
