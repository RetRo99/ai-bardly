package com.retro99.chats.data.local.model

import com.retro99.chats.domain.model.MessageDomainModel
import com.retro99.database.api.message.MessageEntity
import kotlinx.datetime.LocalDateTime

data class MessageLocalModel(
    override val gameId: String,
    override val question: String,
    override val gameTitle: String,
    override val timestamp: LocalDateTime,
    override val answer: String,
) : MessageEntity


fun MessageEntity.toDomainModel() = MessageDomainModel(
    question = question,
    answer = answer,
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun List<MessageEntity>.toDomainModel() = map(MessageEntity::toDomainModel)

fun MessageDomainModel.toLocalModel() = MessageLocalModel(
    question = question,
    answer = requireNotNull(answer) { "answer must be provided when saving to database" },
    gameId = gameId,
    timestamp = timestamp,
    gameTitle = gameTitle,
)

fun List<MessageDomainModel>.toLocalModel() = map(MessageDomainModel::toLocalModel)
