package com.retro99.shelfs.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ShelfsListDto(
    val shelfs: List<ShelfDto>,
)
