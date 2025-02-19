package com.ai.bardly.feature.auth.data.tokens

import com.ai.bardly.networking.tokens.BearerTokenProvider
import com.ai.bardly.settings.BardySettingKey
import com.ai.bardly.settings.BardySettings
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn


@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class StoredTokenProvider(
    private val settings: BardySettings
) : BearerTokenProvider {

    override suspend fun getBearerToken(): String? {
        return settings.getStringOrNull(BardySettingKey.AccessToken)
    }

}
