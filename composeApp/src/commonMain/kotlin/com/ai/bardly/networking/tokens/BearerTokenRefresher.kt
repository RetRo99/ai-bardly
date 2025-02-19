package com.ai.bardly.networking.tokens

interface BearerTokenRefresher {
    suspend fun refreshBearerToken(): String?
}
