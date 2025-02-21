package com.retro99.preferences

interface Preferences {
    fun getStringOrNull(key: PreferencesKey): String?
    fun putString(key: PreferencesKey, value: String)
}

enum class PreferencesKey {
    AccessToken
}