package com.retro99.database.implementation.dao.shelfs

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.retro99.database.api.shelfs.ShelfEntity

@Entity
data class RoomShelfEntity(
    @PrimaryKey()
    override val id: String,
    override val name: String,
    override val description: String? = null,
    override val games: List<String>,
) : ShelfEntity

fun ShelfEntity.toRoomEntity() = RoomShelfEntity(
    id = id,
    name = name,
    description = description,
    games = games
)

fun List<ShelfEntity>.toRoomEntity() = map(ShelfEntity::toRoomEntity)
