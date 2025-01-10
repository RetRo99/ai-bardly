package com.ai.bardly

import com.ai.bardly.data.GameApiModel
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
    val listNumber: Int
)

fun List<GameApiModel>.toUiModels() = map(GameApiModel::toUiModel)

fun GameApiModel.toUiModel() = GameUiModel(
    title = title,
    description = description,
    rating = rating,
    yearPublished = yearPublished,
    numberOfPlayers = numberOfPlayers.substringBefore(" "),
    playingTime = playingTime,
    ageRange = ageRange.substringAfter(" "),
    complexity = complexity,
    link = link,
    thumbnail = thumbnail,
    listNumber = listNumber,
)
