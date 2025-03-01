package com.retro99.data.remote.model

import com.retro99.auth.domain.model.UserDomainModel

data class UserDto(
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