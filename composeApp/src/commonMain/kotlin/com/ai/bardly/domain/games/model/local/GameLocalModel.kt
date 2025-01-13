package com.ai.bardly.domain.games.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ai.bardly.domain.chats.local.MessageLocalModel
import com.ai.bardly.domain.chats.model.MessageDomainModel
import com.ai.bardly.domain.games.model.GameDomainModel
import com.ai.bardly.paging.PagingItem

@Entity
data class GameLocalModel(
    val title: String,
    val description: String,
    val rating: String,
    val yearPublished: String,
    val numberOfPlayers: String,
    val playingTime: String,
    val ageRange: String,
    val complexity: String,
    val link: String,
    val thumbnail: String,
    @PrimaryKey
    override val id: Int
) : PagingItem

fun GameLocalModel.toDomainModel() = GameDomainModel(
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
)

fun GameDomainModel.toLocalModel() = GameLocalModel(
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
)

fun MessageDomainModel.toLocalModel(): MessageLocalModel = MessageLocalModel(
    id = id,
    text = text,
    type = type,
    timestamp = timestamp,
)