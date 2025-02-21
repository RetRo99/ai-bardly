package com.ai.bardly.feature.auth.data.tokens

import com.retro99.network.tokens.BearerTokenProvider
import com.retro99.preferences.Preferences
import com.retro99.preferences.PreferencesKey
import me.tatarka.inject.annotations.Inject
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
