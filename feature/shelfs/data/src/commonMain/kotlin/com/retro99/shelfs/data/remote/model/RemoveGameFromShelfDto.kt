package com.retro99.shelfs.data.remote.model

import com.retro99.shelfs.domain.model.RemoveGameFromShelfDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoveGameFromShelfDto(
    @SerialName("gameId")
    val gameId: String,
    @SerialName("shelfId")
    val shelfId: String
)

fun RemoveGameFromShelfDomainModel.toDto() = RemoveGameFromShelfDto(
    gameId = gameId,
    shelfId = shelfId
)