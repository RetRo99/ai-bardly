package com.ai.bardly.feature.games.domain.model

import com.ai.bardly.paging.PagingItem

data class GameDomainModel(
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
    override val id: Int
) : PagingItem
