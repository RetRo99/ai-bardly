package com.ai.bardly.analytics

import co.touchlab.kermit.Logger

class DebugAnalyticsManager : Analytics {

    private val logger = Logger.withTag("DebugAnalyticsManager")

    override fun log(event: AnalyticsEvent) {
        logger.d { "Logging event: $event" }
    }

    override fun logException(throwable: Throwable, message: String?) {
        logger.e(throwable) {
            if (message.isNullOrEmpty()) {
                "Exception occurred"
            } else {
                "Exception occurred with message: $message"
            }
        }
    }
}