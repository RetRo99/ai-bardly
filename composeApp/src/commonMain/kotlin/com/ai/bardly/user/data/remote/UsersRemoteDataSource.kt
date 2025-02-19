package com.ai.bardly.user.data.remote

import com.ai.bardly.user.data.remote.model.UserDto

interface UsersRemoteDataSource {
    suspend fun getCurrentUser(): UserDto?
}