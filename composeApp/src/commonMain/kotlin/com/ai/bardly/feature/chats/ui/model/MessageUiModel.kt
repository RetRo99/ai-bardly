package com.ai.bardly.feature.chats.ui.model

import com.ai.bardly.feature.chats.domain.model.MessageDomainModel
import com.ai.bardly.feature.chats.domain.model.MessageType
import com.ai.bardly.util.now
import kotlinx.datetime.LocalDateTime

data class MessageUiModel(
    val text: String,
    val type: MessageType,
    val gameId: Int,
    val gameTitle: String,
    val animateText: Boolean = false,
    val timestamp: LocalDateTime = now(),
) {
    val isUserMessage: Boolean
        get() = type == MessageType.User
}

fun MessageUiModel.toDomainModel() = MessageDomainModel(
    text = text,
    type = type,
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun MessageDomainModel.toUiModel(animateText: Boolean) = MessageUiModel(
    text = text,
    type = type,
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
    animateText = animateText,
)