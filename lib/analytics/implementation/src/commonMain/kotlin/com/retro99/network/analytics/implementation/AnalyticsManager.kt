package com.retro99.network.analytics.implementation

import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics
import retro99.analytics.api.Analytics
import retro99.analytics.api.AnalyticsEvent

class AnalyticsManager(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val firebaseCrashlytics: FirebaseCrashlytics,
) : Analytics {

    override fun log(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(
            event.name,
            event.paramsMap
        )
    }

    override fun logException(throwable: Throwable, message: String?) {
        message?.let { firebaseCrashlytics.log(it) }
        firebaseCrashlytics.recordException(throwable)
    }
}