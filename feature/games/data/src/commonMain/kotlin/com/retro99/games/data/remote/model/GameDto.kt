package com.retro99.games.data.remote.model

import com.retro99.games.domain.model.GameDomainModel
import com.retro99.paging.domain.PagingItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
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
    @SerialName("_id")
    override val id: Int,
    val categories: List<String>?,
    val types: List<String>?,
) : PagingItem

fun List<GameDto>.toDomainModel() = map(GameDto::toDomainModel)

fun GameDto.toDomainModel() = GameDomainModel(
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
    categories = categories,
    types = types,
)
