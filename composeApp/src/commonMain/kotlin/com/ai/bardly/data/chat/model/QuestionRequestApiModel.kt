package com.ai.bardly.data.chat.model

import com.ai.bardly.domain.chats.model.MessageDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class QuestionRequestApiModel(
    val question: String,
    val game: Int
)

fun MessageDomainModel.toRequest() = QuestionRequestApiModel(
    question = text,
    game = gameId
)