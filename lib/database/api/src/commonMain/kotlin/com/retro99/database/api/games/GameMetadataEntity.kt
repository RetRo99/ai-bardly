package com.retro99.database.api.games

import kotlinx.datetime.LocalDateTime

interface GameMetadataEntity {
    val gameId: String
    val lastOpenTime: LocalDateTime?
    val isFavourite: Boolean
}
