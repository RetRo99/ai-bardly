package com.retro99.chats.domain.model

import com.retro99.base.now
import kotlinx.datetime.LocalDateTime

data class MessageDomainModel(
    val question: String,
    val answer: String?,
    val gameId: Int,
    val gameTitle: String,
    val timestamp: LocalDateTime = now(),
)