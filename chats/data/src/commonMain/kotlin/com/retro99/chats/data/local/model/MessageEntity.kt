package com.retro99.chats.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.retro99.chats.domain.model.MessageDomainModel
import kotlinx.datetime.LocalDateTime

@Entity
data class MessageEntity(
    val gameId: Int,
    val text: String,
    val type: MessageLocalType,
    val gameTitle: String,
    @PrimaryKey
    val timestamp: LocalDateTime,
)

fun MessageEntity.toDomainModel() = MessageDomainModel(
    gameId = gameId,
    text = text,
    type = type.toDomainType(),
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun List<MessageEntity>.toDomainModel() = map(MessageEntity::toDomainModel)

fun MessageDomainModel.toEntity() = MessageEntity(
    gameId = gameId,
    text = text,
    type = type.toLocalType(),
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun List<MessageDomainModel>.toEntity() = map(MessageDomainModel::toEntity)