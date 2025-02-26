package com.retro99.preferences.implementation

import com.russhwolf.settings.Settings
import me.tatarka.inject.annotations.Inject
import retro99.preferences.api.Preferences
import retro99.preferences.api.PreferencesKey
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class MultiplatformPreferences(
    private val settings: Settings,
) : Preferences {
    override fun getStringOrNull(key: PreferencesKey): String? = settings.getStringOrNull(key.name)

    override fun putString(key: PreferencesKey, value: String) =
        settings.putString(key.name, value)

    override fun remove(key: PreferencesKey) {
        settings.remove(key.name)
    }
}