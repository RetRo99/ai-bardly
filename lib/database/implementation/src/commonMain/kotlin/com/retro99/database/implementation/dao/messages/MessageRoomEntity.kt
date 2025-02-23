package com.retro99.database.implementation.dao.messages

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.retro99.database.api.MessageEntity
import com.retro99.database.api.MessageLocalType
import kotlinx.datetime.LocalDateTime

@Entity
data class MessageRoomEntity(
    override val gameId: Int,
    override val text: String,
    override val type: MessageLocalType,
    override val gameTitle: String,
    @PrimaryKey
    override val timestamp: LocalDateTime,
) : MessageEntity

fun MessageEntity.toRoomEntity(): MessageRoomEntity = MessageRoomEntity(
    gameId = gameId,
    text = text,
    type = type,
    gameTitle = gameTitle,
    timestamp = timestamp,
)

fun List<MessageEntity>.toRoomEntity(): List<MessageRoomEntity> = map(MessageEntity::toRoomEntity)
