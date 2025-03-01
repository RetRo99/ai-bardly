package com.retro99.auth.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBearerTokenResponseDto(
    @SerialName("access_token")
    val accessToken: String,
)
