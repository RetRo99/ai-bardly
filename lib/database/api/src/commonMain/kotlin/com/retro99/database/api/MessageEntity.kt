package com.retro99.database.api

import kotlinx.datetime.LocalDateTime

interface MessageEntity {
    val gameId: Int
    val text: String
    val type: MessageLocalType
    val gameTitle: String
    val timestamp: LocalDateTime
}