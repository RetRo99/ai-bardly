package com.retro99.chats.data.remote.model

import com.retro99.base.now
import com.retro99.chats.domain.model.MessageDomainModel
import kotlinx.datetime.LocalDateTime

data class MessageDto(
    val question: String,
    val answer: String?,
    val gameId: Int,
    val gameTitle: String,
    val timestamp: LocalDateTime = now(),
)

fun MessageDomainModel.toDto() = MessageDto(
    question = question,
    answer = answer,
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun MessageDto.toDomainModel() = MessageDomainModel(
    question = question,
    answer = answer,
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
)
