package com.retro99.user.domain.model

data class UserDomainModel(
    val id: String,
    val email: String?,
    val displayName: String?,
    val isEmailVerified: Boolean,
)
