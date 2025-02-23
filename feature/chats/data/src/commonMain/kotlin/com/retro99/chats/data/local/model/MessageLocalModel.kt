package com.retro99.chats.data.local.model

import com.retro99.chats.domain.model.MessageDomainModel
import com.retro99.chats.domain.model.MessageType
import com.retro99.database.api.MessageEntity
import com.retro99.database.api.MessageLocalType
import kotlinx.datetime.LocalDateTime

data class MessageLocalModel(
    override val gameId: Int,
    override val text: String,
    override val type: MessageLocalType,
    override val gameTitle: String,
    override val timestamp: LocalDateTime,
) : MessageEntity


fun MessageEntity.toDomainModel() = MessageDomainModel(
    gameId = gameId,
    text = text,
    type = type.toDomainType(),
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun MessageLocalType.toDomainType() = when (this) {
    MessageLocalType.User -> MessageType.User
    MessageLocalType.Bardly -> MessageType.Bardly
}

fun MessageType.toLocalType() = when (this) {
    MessageType.User -> MessageLocalType.User
    MessageType.Bardly -> MessageLocalType.Bardly
}

fun List<MessageEntity>.toDomainModel() = map(MessageEntity::toDomainModel)

fun MessageDomainModel.toLocalModel() = MessageLocalModel(
    gameId = gameId,
    text = text,
    type = type.toLocalType(),
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun List<MessageDomainModel>.toLocalModel() = map(MessageDomainModel::toLocalModel)