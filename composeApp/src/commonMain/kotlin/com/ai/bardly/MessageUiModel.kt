package com.ai.bardly

import com.ai.bardly.domain.chats.model.MessageDomainModel
import com.ai.bardly.domain.chats.model.MessageType
import com.ai.bardly.util.now
import kotlinx.datetime.LocalDateTime

data class MessageUiModel(
    val text: String,
    val type: MessageType,
    val gameId: Int,
    val timestamp: LocalDateTime = now(),
) {
    val isUserMessage: Boolean
        get() = type == MessageType.User
}

fun MessageUiModel.toDomainModel() = MessageDomainModel(
    text = text,
    type = type,
    gameId = gameId,
    timestamp = timestamp
)

fun MessageDomainModel.toUiModel() = MessageUiModel(
    text = text,
    type = type,
    gameId = gameId,
    timestamp = timestamp
)