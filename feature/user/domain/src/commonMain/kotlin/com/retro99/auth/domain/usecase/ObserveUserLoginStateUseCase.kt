package com.retro99.auth.domain.usecase

import com.retro99.auth.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
class ObserveUserLoginStateUseCase(
    private val userRepository: UserRepository,
) {

    operator fun invoke(): Flow<Boolean> {
        return userRepository.observeCurrentUser().map {
            it != null
        }
    }
}