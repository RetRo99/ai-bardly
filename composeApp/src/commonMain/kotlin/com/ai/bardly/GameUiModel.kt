package com.ai.bardly

import androidx.paging.PagingData
import app.cash.paging.map
import com.ai.bardly.data.GameApiModel
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
): PagingItem


fun PagingData<GameApiModel>.toUiModels() = map(GameApiModel::toUiModel)
fun Flow<PagingData<GameApiModel>>.toUiModels() = map { it.toUiModels() }

fun GameApiModel.toUiModel() = GameUiModel(
    title = title.orEmpty(),
    description = description.orEmpty(),
    rating = rating.orEmpty(),
    yearPublished = yearPublished.orEmpty(),
    numberOfPlayers = numberOfPlayers.orEmpty().substringBefore(" "),
    playingTime = playingTime.orEmpty(),
    ageRange = ageRange.orEmpty().substringAfter(" "),
    complexity = complexity.orEmpty(),
    link = link.orEmpty(),
    thumbnail = thumbnail.orEmpty(),
    id = id,
)
