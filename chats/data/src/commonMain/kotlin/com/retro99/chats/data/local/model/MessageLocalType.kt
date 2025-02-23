package com.retro99.chats.data.local.model

import com.retro99.chats.domain.model.MessageType

enum class MessageLocalType {
    User,
    Bardly
}

fun MessageLocalType.toDomainType() = when (this) {
    MessageLocalType.User -> MessageType.User
    MessageLocalType.Bardly -> MessageType.Bardly
}

fun MessageType.toLocalType() = when (this) {
    MessageType.User -> MessageLocalType.User
    MessageType.Bardly -> MessageLocalType.Bardly
}