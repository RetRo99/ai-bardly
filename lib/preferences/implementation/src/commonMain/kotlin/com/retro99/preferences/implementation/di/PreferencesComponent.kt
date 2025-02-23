package com.retro99.preferences.implementation.di

import com.russhwolf.settings.Settings
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
@Component
interface PreferencesComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideSettings(
        factory: Settings.Factory
    ): Settings {
        return factory.create("SecureSettings")
    }
}