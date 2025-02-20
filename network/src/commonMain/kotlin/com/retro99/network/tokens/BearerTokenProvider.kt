package com.retro99.network.tokens

interface BearerTokenProvider {
    suspend fun getBearerToken(): String?
}
