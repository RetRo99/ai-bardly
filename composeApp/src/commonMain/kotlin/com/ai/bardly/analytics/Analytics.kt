package com.ai.bardly.analytics

interface Analytics {
    fun log(event: AnalyticsEvent, params: Map<String, String>)
    fun log(event: AnalyticsEvent, key: String, value: String)
    fun logException(throwable: Throwable, message: String?)
}