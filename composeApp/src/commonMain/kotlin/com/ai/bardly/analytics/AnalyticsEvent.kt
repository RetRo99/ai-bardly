package com.ai.bardly.analytics

sealed class AnalyticsEvent(val name: String, val params: Map<String, String>) {
    data class ScreenOpen(
        val screenName: String,
        val additionalParams: Map<String, String>
    ) : AnalyticsEvent(
        name = "screen_open",
        params = mapOf("screen_name" to screenName) + additionalParams
    ) {
        constructor(
            screenName: String,
            vararg params: Pair<AnalyticsParam<*>, Any>
        ) : this(
            screenName,
            params.toParamsMap()
        )
    }
}