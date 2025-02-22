package com.retro99.database.implementation.toremove

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

//TODO(remove)
@Entity
data class MessageEntity(
    val gameId: Int,
    val text: String,
    val gameTitle: String,
    @PrimaryKey
    val timestamp: LocalDateTime,
)
