package com.ai.bardly.domain.chats.model

import com.ai.bardly.util.now
import kotlinx.datetime.LocalDateTime

data class MessageDomainModel(
    val text: String,
    val type: MessageType,
    val id: String,
    val timestamp: LocalDateTime = now(),
) {
    val isUserMessage: Boolean
        get() = type == MessageType.User
}