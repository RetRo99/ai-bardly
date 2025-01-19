package com.ai.bardly.feature.chats.data.remote.model

import com.ai.bardly.feature.chats.domain.model.MessageDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class QuestionRequestDto(
    val question: String,
    val game: String
)

fun MessageDomainModel.toRequest() = QuestionRequestDto(
    question = text,
    game = gameTitle,
)