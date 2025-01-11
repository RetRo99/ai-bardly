package com.ai.bardly.analytics

import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics

class AnalyticsManager(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebaseCrashlytics: FirebaseCrashlytics,
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

    override fun logException(throwable: Throwable, message: String?) {
        message?.let { firebaseCrashlytics.log(it) }
        firebaseCrashlytics.recordException(throwable)
    }
}