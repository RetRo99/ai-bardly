package com.ai.bardly.analytics

import co.touchlab.kermit.Logger

class DebugAnalyticsManager : Analytics {

    private val logger = Logger.withTag("DebugAnalyticsManager")

    override fun log(eventName: String, params: Map<String, String>) {
        logger.v { "log event: $eventName params: $params" }
    }

    override fun log(eventName: String, key: String, value: String) {
        // No analytics for debug, but we can log using kermit
        logger.v { "log event: $eventName, key: $key, value: $value" }
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