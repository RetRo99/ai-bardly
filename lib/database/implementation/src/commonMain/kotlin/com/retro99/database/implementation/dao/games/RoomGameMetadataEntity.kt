package com.retro99.database.implementation.dao.games

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.retro99.database.api.games.GameMetadataEntity
import kotlinx.datetime.LocalDateTime

@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomGameEntity::class,
        parentColumns = ["id"],
        childColumns = ["gameId"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class RoomGameMetadataEntity(
    @PrimaryKey override val gameId: Int,
    override val lastOpenTime: LocalDateTime
) : GameMetadataEntity

fun GameMetadataEntity.toRoomEntity() = RoomGameMetadataEntity(
    gameId = gameId,
    lastOpenTime = lastOpenTime,
)