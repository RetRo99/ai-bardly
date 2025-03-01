package com.retro99.auth.domain.model

data class UserDomainModel(
    val id: String,
    val email: String?,
    val displayName: String?,
    val isEmailVerified: Boolean,
)
