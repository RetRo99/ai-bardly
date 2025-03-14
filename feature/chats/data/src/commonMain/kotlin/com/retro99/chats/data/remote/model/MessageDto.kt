package com.retro99.chats.data.remote.model

import com.retro99.chats.domain.model.MessageDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val question: String,
    val answer: String?,
)

fun MessageDomainModel.toDto() = MessageDto(
    question = question,
    answer = answer,
)
