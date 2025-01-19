package com.ai.bardly

import androidx.paging.PagingData
import androidx.paging.map
import com.ai.bardly.feature.games.domain.model.GameDomainModel
import com.ai.bardly.paging.PagingItem
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
    override val id: Int
) : PagingItem

fun PagingData<GameDomainModel>.toUiModels() = map(GameDomainModel::toUiModel)
fun Flow<PagingData<GameDomainModel>>.toUiModels() = map { it.toUiModels() }

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
)
