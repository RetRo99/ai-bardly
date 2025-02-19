package com.ai.bardly.feature.auth.data

interface TokenRefresher {
    suspend fun refreshBearerToken(): String?
}
