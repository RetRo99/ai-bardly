package com.bardly.chats.ui.model

import com.retro99.base.now
import com.retro99.chats.domain.model.MessageDomainModel
import kotlinx.datetime.LocalDateTime

data class MessageUiModel(
    val question: String,
    val answer: String?,
    val gameId: Int,
    val gameTitle: String,
    val animateText: Boolean = false,
    val timestamp: LocalDateTime = now(),
)

fun MessageUiModel.toDomainModel() = MessageDomainModel(
    question = question,
    answer = answer,
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun MessageDomainModel.toUiModel() = MessageUiModel(
    question = question,
    answer = answer,
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
)