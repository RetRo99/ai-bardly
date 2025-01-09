package com.ai.bardly.analytics

class DebugAnalyticsManager: Analytics {

    override fun log(eventName: String, params: Map<String, String>) {
        // No analytics for debug
    }

    override fun log(eventName: String, key: String, value: String) {
        // No analytics for debug
    }

}