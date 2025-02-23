package com.retro99.network.analytics.implementation.di

import com.retro99.base.annotations.Named
import com.retro99.network.analytics.implementation.AnalyticsManager
import com.retro99.network.analytics.implementation.DebugAnalyticsManager
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.crashlytics.FirebaseCrashlytics
import dev.gitlive.firebase.crashlytics.crashlytics
import me.tatarka.inject.annotations.Provides
import retro99.analytics.api.Analytics
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
interface AnalyticsComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Provides
    @SingleIn(AppScope::class)
    fun provideCrashlytics(): FirebaseCrashlytics = Firebase.crashlytics

    @Provides
    @SingleIn(AppScope::class)
    fun provideAnalytics(
        firebaseAnalytics: FirebaseAnalytics,
        firebaseCrashlytics: FirebaseCrashlytics,
        @Named("isDebug") isDebug: Boolean,
    ): Analytics = if (isDebug) DebugAnalyticsManager() else AnalyticsManager(
        firebaseAnalytics,
        firebaseCrashlytics
    )
}