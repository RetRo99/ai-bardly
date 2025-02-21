package com.retro99.preferences

import com.russhwolf.settings.Settings
import me.tatarka.inject.annotations.Inject
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
}