package com.ai.bardly.feature.chats.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PromptResponseDto(
    val data: ResponseDataDto
)