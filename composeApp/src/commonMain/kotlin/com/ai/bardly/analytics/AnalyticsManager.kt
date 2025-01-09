package com.ai.bardly.analytics

import dev.gitlive.firebase.analytics.FirebaseAnalytics

class AnalyticsManager(
    private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {

    override fun log(eventName: String, params: Map<String, String>) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    override fun log(eventName: String, key: String, value: String) {
        val params = mapOf(
            key to value,
        )
        firebaseAnalytics.logEvent(eventName, params)
    }

}