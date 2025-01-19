package com.ai.bardly.feature.games.data.remote.model

import com.ai.bardly.feature.games.domain.model.GameDomainModel
import com.ai.bardly.paging.PagingItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameApiModel(
    val title: String?,
    val description: String?,
    val rating: String?,
    val yearPublished: String?,
    val numberOfPlayers: String?,
    val playingTime: String?,
    val ageRange: String?,
    val complexity: String?,
    val link: String?,
    val thumbnail: String?,
    @SerialName("listNumber")
    override val id: Int,
) : PagingItem

fun List<GameApiModel>.toDomainModel() = map(GameApiModel::toDomainModel)

fun GameApiModel.toDomainModel() = GameDomainModel(
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
