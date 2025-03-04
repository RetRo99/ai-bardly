package com.retro99.auth.data.tokens

import com.retro99.preferences.api.Preferences
import com.retro99.preferences.api.PreferencesKey
import me.tatarka.inject.annotations.Inject
import retro99.games.api.tokens.BearerTokenProvider
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding


@Inject
@ContributesBinding(AppScope::class)
class StoredTokenProvider(
    private val preferences: Preferences
) : BearerTokenProvider {

    override suspend fun getBearerToken(): String? {
        return preferences.getStringOrNull(PreferencesKey.AccessToken)
    }

}
