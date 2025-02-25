package com.retro99.auth.domain.manager

import kotlinx.coroutines.flow.StateFlow

interface UserSessionManager {
    val isUserLoggedIn: StateFlow<Boolean>
}