package com.ai.bardly.analytics

import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics

class AnalyticsManager(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebaseCrashlytics: FirebaseCrashlytics,
) : Analytics {

    override fun log(event: AnalyticsEvent, params: Map<String, String>) {
        firebaseAnalytics.logEvent(event.name, params)
    }

    override fun log(event: AnalyticsEvent, key: String, value: String) {
        val params = mapOf(
            key to value,
        )
        firebaseAnalytics.logEvent(event.name, params)
    }

    override fun logException(throwable: Throwable, message: String?) {
        message?.let { firebaseCrashlytics.log(it) }
        firebaseCrashlytics.recordException(throwable)
    }
}