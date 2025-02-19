package com.ai.bardly.user.data.remote.model

data class UserDto(
    val id: String,
    val email: String?,
    val displayName: String?,
    val isEmailVerified: Boolean,
)