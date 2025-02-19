package com.ai.bardly.user.domain

import com.ai.bardly.user.domain.model.UserDomainModel

interface UserRepository {
    suspend fun getCurrentUser(): UserDomainModel?
}