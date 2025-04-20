package com.retro99.shelfs.domain.model

import com.retro99.games.domain.model.GameDomainModel
import com.retro99.paging.domain.PagingItem

data class ShelfDomainModel(
    override val id: String,
    val name: String,
    val games: List<GameDomainModel>,
) : PagingItem
