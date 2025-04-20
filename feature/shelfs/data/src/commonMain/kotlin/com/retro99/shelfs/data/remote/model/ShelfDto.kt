package com.retro99.shelfs.data.remote.model

import com.retro99.games.data.remote.model.GameDto
import com.retro99.paging.domain.PagingItem
import com.retro99.shelfs.domain.model.ShelfDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShelfDto(
    @SerialName("_id")
    override val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("games")
    val games: List<GameDto>,
) : PagingItem

fun List<ShelfDto>.toDomainModel() = map(ShelfDto::toDomainModel)

fun ShelfDto.toDomainModel() = ShelfDomainModel(
    id = id,
)
