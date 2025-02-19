package com.ai.bardly.feature.auth.data.remote

import com.ai.bardly.networking.NetworkClient
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class NetworkAuthRemoteDataSource(
    private val networkClient: NetworkClient
) : AuthRemoteDataSource
