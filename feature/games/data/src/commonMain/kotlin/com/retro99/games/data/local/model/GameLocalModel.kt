package com.retro99.games.data.local.model

import androidx.paging.PagingData
import androidx.paging.map
import com.retro99.database.api.games.GameEntity
import com.retro99.games.domain.model.GameDomainModel
import com.retro99.paging.domain.PagingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class GameLocalModel(
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
    override val id: String,
    override val categories: List<String>?,
    override val types: List<String>?,
) : PagingItem, GameEntity

fun Flow<PagingData<GameEntity>>.toDomainModel() = map { it.map(GameEntity::toDomainModel) }

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
    categories = categories,
    types = types,
)

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
    categories = categories,
    types = types,
)

fun List<GameEntity>.toDomainModel() = map(GameEntity::toDomainModel)

fun List<GameDomainModel>.toLocalModel() = map(GameDomainModel::toLocalModel)
