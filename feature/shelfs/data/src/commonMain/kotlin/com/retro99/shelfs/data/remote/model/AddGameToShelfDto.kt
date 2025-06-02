package com.retro99.shelfs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddGameToShelfDto(
    @SerialName("gameId")
    val gameId: String
)
