package com.ai.bardly.feature.auth.data

import com.ai.bardly.settings.BardySettingKey
import com.ai.bardly.settings.BardySettings
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn


@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class FirebaseTokenProvider(
    private val settings: BardySettings
) : TokenProvider {

    override suspend fun getBearerToken(): String? {
        return settings.getStringOrNull(BardySettingKey.AccessToken)
    }

}
