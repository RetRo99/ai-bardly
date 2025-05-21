package com.retro99.database.implementation.dao.messages

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.retro99.database.api.message.MessageEntity
import kotlinx.datetime.LocalDateTime

@Entity
data class MessageRoomEntity(
    override val gameId: String,
    override val question: String,
    override val gameTitle: String,
    @PrimaryKey
    override val timestamp: LocalDateTime,
    override val answer: String,
) : MessageEntity

fun MessageEntity.toRoomEntity(): MessageRoomEntity = MessageRoomEntity(
    question = question,
    answer = answer,
    gameId = gameId,
    gameTitle = gameTitle,
    timestamp = timestamp,
)

fun List<MessageEntity>.toRoomEntity(): List<MessageRoomEntity> = map(MessageEntity::toRoomEntity)
