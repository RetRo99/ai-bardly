package com.ai.bardly.analytics

interface Analytics {
    fun logException(throwable: Throwable, message: String?)
    fun log(event: AnalyticsEvent)
}