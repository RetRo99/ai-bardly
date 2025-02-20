package com.retro99.network.tokens

interface BearerTokenRefresher {
    suspend fun refreshBearerToken(): String?
}
