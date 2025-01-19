package com.ai.bardly.feature.chats.data.remote.model

import com.ai.bardly.feature.chats.domain.model.MessageDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class QuestionRequestApiModel(
    val question: String,
    val game: String
)

fun MessageDomainModel.toRequest() = QuestionRequestApiModel(
    question = text,
    game = gameTitle,
)