package retro99.preferences.api

interface Preferences {
    fun getStringOrNull(key: PreferencesKey): String?
    fun putString(key: PreferencesKey, value: String)
}

enum class PreferencesKey {
    AccessToken,
}