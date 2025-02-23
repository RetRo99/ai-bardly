package com.retro99.chats.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionRequestDto(
    val question: String,
    val game: String
)

fun MessageDto.toRequest() = QuestionRequestDto(
    question = text,
    game = gameTitle,
)