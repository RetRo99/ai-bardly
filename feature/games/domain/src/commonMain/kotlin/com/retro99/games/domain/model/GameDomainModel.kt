package com.retro99.games.domain.model

import com.retro99.paging.domain.PagingItem

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