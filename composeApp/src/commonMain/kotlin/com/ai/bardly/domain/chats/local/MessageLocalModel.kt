package com.ai.bardly.domain.chats.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ai.bardly.domain.chats.model.MessageDomainModel
import com.ai.bardly.domain.chats.model.MessageType
import kotlinx.datetime.LocalDateTime

@Entity
data class MessageLocalModel(
    val id: String,
    val text: String,
    val type: MessageType,
    @PrimaryKey
    val timestamp: LocalDateTime,
)

fun MessageLocalModel.toDomainModel() = MessageDomainModel(
    id = id,
    text = text,
    type = type,
    timestamp = timestamp,
)

fun MessageDomainModel.toLocalModel() = MessageLocalModel(
    id = id,
    text = text,
    type = type,
    timestamp = timestamp,
)