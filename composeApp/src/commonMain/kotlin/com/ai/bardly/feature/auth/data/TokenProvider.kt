package com.ai.bardly.feature.auth.data

interface TokenProvider {
    suspend fun getBearerToken(): String?
}
