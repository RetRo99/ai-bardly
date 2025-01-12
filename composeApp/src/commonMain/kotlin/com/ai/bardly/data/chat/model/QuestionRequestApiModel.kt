package com.ai.bardly.data.chat.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionRequestApiModel(
    val question: String,
    val game: String
)