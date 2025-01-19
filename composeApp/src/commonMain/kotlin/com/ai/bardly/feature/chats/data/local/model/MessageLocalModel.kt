package com.ai.bardly.feature.chats.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ai.bardly.feature.chats.domain.model.MessageDomainModel
import com.ai.bardly.feature.chats.domain.model.MessageType
import kotlinx.datetime.LocalDateTime

@Entity
data class MessageLocalModel(
    val gameId: Int,
    val text: String,
    val type: MessageType,
    val gameTitle: String,
    @PrimaryKey
    val timestamp: LocalDateTime,
)

fun MessageLocalModel.toDomainModel() = MessageDomainModel(
    gameId = gameId,
    text = text,
    type = type,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun MessageDomainModel.toLocalModel() = MessageLocalModel(
    gameId = gameId,
    text = text,
    type = type,
    timestamp = timestamp,
    gameTitle = gameTitle,
)