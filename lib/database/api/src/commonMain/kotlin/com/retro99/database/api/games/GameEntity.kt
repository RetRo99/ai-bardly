package com.retro99.database.api.games

import com.retro99.paging.domain.PagingItem

interface GameEntity : PagingItem {
    val title: String
    val description: String
    val rating: String
    val yearPublished: String
    val numberOfPlayers: String
    val playingTime: String
    val ageRange: String
    val complexity: String
    val link: String
    val thumbnail: String
    override val id: Int
    val categories: List<String>?
    val types: List<String>?
}