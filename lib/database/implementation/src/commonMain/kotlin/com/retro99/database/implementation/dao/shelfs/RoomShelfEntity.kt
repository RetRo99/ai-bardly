package com.retro99.database.implementation.dao.shelfs

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.retro99.database.api.shelfs.ShelfEntity

@Entity
data class RoomShelfEntity(
    @PrimaryKey()
    override val id: String,
) : ShelfEntity

fun ShelfEntity.toRoomEntity() = RoomShelfEntity(
    id = id,
)

fun List<ShelfEntity>.toRoomEntity() = map(ShelfEntity::toRoomEntity)