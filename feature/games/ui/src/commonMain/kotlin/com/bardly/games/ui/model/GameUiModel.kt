package com.bardly.games.ui.model

import androidx.paging.PagingData
import androidx.paging.map
import com.retro99.games.domain.model.GameDomainModel
import com.retro99.paging.domain.PagingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class GameUiModel(
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
    override val id: Int,
    val categories: List<String>?,
    val types: List<String>?,
) : PagingItem

fun PagingData<GameDomainModel>.toUiModels() = map(GameDomainModel::toUiModel)
fun Flow<PagingData<GameDomainModel>>.toUiModels() = map { it.toUiModels() }
fun List<GameDomainModel>.toUiModel() = map(GameDomainModel::toUiModel)
fun GameDomainModel.toUiModel() = GameUiModel(
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
