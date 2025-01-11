package com.ai.bardly.analytics

import co.touchlab.kermit.Logger

class DebugAnalyticsManager : Analytics {

    private val logger = Logger.withTag("DebugAnalyticsManager")

    override fun log(event: AnalyticsEvent, params: Map<String, String>) {
        logger.v { "log event: $event params: $params" }
    }

    override fun log(event: AnalyticsEvent, key: String, value: String) {
        // No analytics for debug, but we can log using kermit
        logger.v { "log event: $event, key: $key, value: $value" }
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