package com.retro99.network.analytics.implementation

import co.touchlab.kermit.Logger
import retro99.analytics.api.Analytics
import retro99.analytics.api.AnalyticsEvent

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