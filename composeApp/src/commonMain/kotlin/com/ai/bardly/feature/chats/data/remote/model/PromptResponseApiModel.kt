package com.ai.bardly.feature.chats.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PromptResponseApiModel(
    val data: ResponseDataApiModel
)