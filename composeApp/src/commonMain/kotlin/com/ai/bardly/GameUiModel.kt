package com.ai.bardly

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import com.ai.bardly.data.GameApiModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
    numberOfPlayers = numberOfPlayers,
    playingTime = playingTime,
    ageRange = ageRange,
    complexity = complexity,
    link = link,
    thumbnail = thumbnail,
    listNumber = listNumber,
)

class GameUiModelNavType : NavType<GameUiModel>(isNullableAllowed = false) {
    private val json = Json { ignoreUnknownKeys = true }

    override fun get(bundle: Bundle, key: String): GameUiModel? {
        val jsonString = bundle.getString(key)
        return jsonString?.let {
            json.decodeFromString<GameUiModel>(it)
        }
    }

    override fun put(bundle: Bundle, key: String, value: GameUiModel) {
        val jsonString = json.encodeToString(value)
        bundle.putString(key, jsonString)
    }

    override fun parseValue(value: String): GameUiModel {
        return json.decodeFromString(value)
    }
}