package com.ai.bardly.settings.di

import com.russhwolf.settings.Settings
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
@Component
interface SettingsComponent {

    @Provides
    @SingleIn(AppScope::class)
    fun provideSettings(
        factory: Settings.Factory
    ): Settings {
        return factory.create("SecureSettings")
    }
}