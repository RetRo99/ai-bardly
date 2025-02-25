package com.retro99.auth.domain.manager

import com.ai.bardly.annotations.ActivityScope
import com.retro99.auth.domain.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ActivityScope::class)
@ContributesBinding(ActivityScope::class)
class DefaultUserSessionManager(
    private val userRepository: UserRepository,
    private val scope: CoroutineScope,
) : UserSessionManager {
    private val isLoggedIn: StateFlow<Boolean> = userRepository.observeCurrentUser()
        .map { it != null }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    override val isUserLoggedIn: Boolean
        get() = isLoggedIn.value
}