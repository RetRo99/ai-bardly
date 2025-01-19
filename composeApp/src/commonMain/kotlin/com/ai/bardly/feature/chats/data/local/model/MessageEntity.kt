package com.ai.bardly.feature.chats.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ai.bardly.feature.chats.domain.model.MessageDomainModel
import com.ai.bardly.feature.chats.domain.model.MessageType
import kotlinx.datetime.LocalDateTime

@Entity
data class MessageEntity(
    val gameId: Int,
    val text: String,
    val type: MessageType,
    val gameTitle: String,
    @PrimaryKey
    val timestamp: LocalDateTime,
)

fun MessageEntity.toDomainModel() = MessageDomainModel(
    gameId = gameId,
    text = text,
    type = type,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun List<MessageEntity>.toDomainModel() = map(MessageEntity::toDomainModel)

fun MessageDomainModel.toEntity() = MessageEntity(
    gameId = gameId,
    text = text,
    type = type,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun List<MessageDomainModel>.toEntity() = map(MessageDomainModel::toEntity)