package com.retro99.games.data.local.model

import com.retro99.database.api.games.GameMetadataEntity
import kotlinx.datetime.LocalDateTime

data class GameMetadataLocalModel(
    override val gameId: Int,
    override val lastOpenTime: LocalDateTime
) : GameMetadataEntity