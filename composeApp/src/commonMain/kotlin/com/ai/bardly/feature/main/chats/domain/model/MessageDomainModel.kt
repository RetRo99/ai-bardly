package com.ai.bardly.feature.main.chats.domain.model

import com.ai.bardly.util.now
import kotlinx.datetime.LocalDateTime

data class MessageDomainModel(
    val text: String,
    val type: MessageType,
    val gameId: Int,
    val gameTitle: String,
    val timestamp: LocalDateTime = now(),
)