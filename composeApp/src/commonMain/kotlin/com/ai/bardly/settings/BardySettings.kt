package com.ai.bardly.settings

interface BardySettings {
    fun getStringOrNull(key: BardySettingKey): String?
    fun putString(key: BardySettingKey, value: String)
}

enum class BardySettingKey {
    AccessToken
}