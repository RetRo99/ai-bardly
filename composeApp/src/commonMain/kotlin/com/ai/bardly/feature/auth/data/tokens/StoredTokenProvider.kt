package com.ai.bardly.feature.auth.data.tokens

import me.tatarka.inject.annotations.Inject
import retro99.network.api.tokens.BearerTokenProvider
import retro99.preferences.api.Preferences
import retro99.preferences.api.PreferencesKey
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
