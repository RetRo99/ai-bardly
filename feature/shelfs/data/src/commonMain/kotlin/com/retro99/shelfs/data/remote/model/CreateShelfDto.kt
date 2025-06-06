package com.retro99.shelfs.data.remote.model

import com.retro99.shelfs.domain.model.CreateShelfDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateShelfDto(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String? = null,
)

fun CreateShelfDomainModel.toDto() = CreateShelfDto(
    name = name,
    description = description,
)