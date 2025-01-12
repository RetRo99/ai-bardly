package com.ai.bardly.data.chat.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDataApiModel(
    val result: ResultDataApiModel
)