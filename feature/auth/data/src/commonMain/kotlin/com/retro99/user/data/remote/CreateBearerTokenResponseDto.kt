package com.retro99.user.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBearerTokenResponseDto(
    @SerialName("access_token")
    val accessToken: String,
)
