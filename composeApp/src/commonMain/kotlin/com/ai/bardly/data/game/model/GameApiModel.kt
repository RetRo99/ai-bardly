package com.ai.bardly.data.game.model

import com.ai.bardly.paging.PagingItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

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
    override val id: Int = Random.nextInt(),
) : PagingItem
