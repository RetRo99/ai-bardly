package com.ai.bardly.networking.tokens

interface BearerTokenProvider {
    suspend fun getBearerToken(): String?
}
