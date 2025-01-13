package com.ai.bardly.domain.chats.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ai.bardly.domain.chats.model.MessageDomainModel
import com.ai.bardly.domain.chats.model.MessageType
import kotlinx.datetime.LocalDateTime

@Entity
data class MessageLocalModel(
    val gameId: Int,
    val text: String,
    val type: MessageType,
    @PrimaryKey
    val timestamp: LocalDateTime,
)

fun MessageLocalModel.toDomainModel() = MessageDomainModel(
    gameId = gameId,
    text = text,
    type = type,
    timestamp = timestamp,
)

fun MessageDomainModel.toLocalModel() = MessageLocalModel(
    gameId = gameId,
    text = text,
    type = type,
    timestamp = timestamp,
)