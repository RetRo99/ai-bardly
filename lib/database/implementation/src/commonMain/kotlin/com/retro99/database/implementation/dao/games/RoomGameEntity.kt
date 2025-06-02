package com.retro99.database.implementation.dao.games

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.retro99.database.api.games.GameEntity

@Entity
data class RoomGameEntity(
    override val title: String,
    override val description: String,
    override val rating: String,
    override val yearPublished: String,
    override val numberOfPlayers: String,
    override val playingTime: String,
    override val ageRange: String,
    override val complexity: String,
    override val link: String,
    override val thumbnail: String,
    @PrimaryKey()
    override val id: String,
    override val categories: List<String>?,
    override val types: List<String>?,
) : GameEntity

fun GameEntity.toRoomEntity() = RoomGameEntity(
    title = title,
    description = description,
    rating = rating,
    yearPublished = yearPublished,
    numberOfPlayers = numberOfPlayers,
    playingTime = playingTime,
    ageRange = ageRange,
    complexity = complexity,
    link = link,
    thumbnail = thumbnail,
    id = id,
    categories = categories,
    types = types,
)

fun List<GameEntity>.toRoomEntity() = map(GameEntity::toRoomEntity)
