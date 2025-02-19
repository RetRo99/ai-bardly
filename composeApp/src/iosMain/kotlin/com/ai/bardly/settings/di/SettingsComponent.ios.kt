package com.ai.bardly.settings.di

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
@SingleIn(AppScope::class)
interface IosSettingsComponent {

    @OptIn(ExperimentalSettingsImplementation::class)
    @Provides
    @SingleIn(AppScope::class)
    fun provideSettingsFactory(): Settings.Factory {
        return KeychainSettings.Factory()
    }
}
