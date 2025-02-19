package com.ai.bardly.user.domain.model

import com.ai.bardly.user.data.remote.model.UserDto

data class UserDomainModel(
    val id: String,
    val email: String?,
    val displayName: String?,
    val isEmailVerified: Boolean,
)

fun UserDto.toDomainModel() = UserDomainModel(
    id = id,
    email = email,
    displayName = displayName,
    isEmailVerified = isEmailVerified
)