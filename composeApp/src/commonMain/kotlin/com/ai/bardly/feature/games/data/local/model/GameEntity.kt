package com.ai.bardly.feature.games.data.local.model

import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ai.bardly.feature.games.domain.model.GameDomainModel
import com.ai.bardly.paging.PagingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Entity
data class GameEntity(
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

fun GameEntity.toDomainModel() = GameDomainModel(
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

fun List<GameEntity>.toDomainModel() = map(GameEntity::toDomainModel)
fun Flow<PagingData<GameEntity>>.toDomainModel() = map { it.map(GameEntity::toDomainModel) }

fun GameDomainModel.toEntity() = GameEntity(
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

fun List<GameDomainModel>.toEntity() = map(GameDomainModel::toEntity)