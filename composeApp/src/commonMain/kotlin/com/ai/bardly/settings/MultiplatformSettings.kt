package com.ai.bardly.settings

import com.russhwolf.settings.Settings
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class MultiplatformSettings(
    private val settings: Settings
) : BardySettings {
    override fun getStringOrNull(key: BardySettingKey): String? = settings.getStringOrNull(key.name)

    override fun putString(key: BardySettingKey, value: String) =
        settings.putString(key.name, value)
}