package com.retro99.database.api.message

import kotlinx.datetime.LocalDateTime

interface MessageEntity {
    val gameId: String
    val question: String
    val answer: String
    val gameTitle: String
    val timestamp: LocalDateTime
}
