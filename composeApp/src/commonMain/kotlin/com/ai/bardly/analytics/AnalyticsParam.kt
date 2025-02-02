package com.ai.bardly.analytics

sealed class AnalyticsParam<T>(val key: String) {
    data object GameTitle : AnalyticsParam<String>("game_title")
}

fun Array<out Pair<AnalyticsParam<*>, Any>>.toParamsMap(): Map<String, String> {
    return associate { (param, value) -> param.key to value.toString() }
}
