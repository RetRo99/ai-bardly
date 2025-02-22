package com.ai.bardly.feature.main.chats.data.remote.model

import com.ai.bardly.feature.main.chats.domain.model.MessageDomainModel
import com.ai.bardly.feature.main.chats.domain.model.MessageType
import com.retro99.base.now
import kotlinx.datetime.LocalDateTime

data class MessageDto(
    val text: String,
    val type: MessageType,
    val gameId: Int,
    val gameTitle: String,
    val timestamp: LocalDateTime = now(),
)

fun MessageDomainModel.toDto() = MessageDto(
    text = text,
    type = type,
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun MessageDto.toDomainModel() = MessageDomainModel(
    gameId = gameId,
    text = text,
    type = type,
    timestamp = timestamp,
    gameTitle = gameTitle,
)
