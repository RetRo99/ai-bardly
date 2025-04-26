package com.retro99.shelfs.data.local.model

import com.retro99.database.api.games.GameEntity
import com.retro99.database.api.shelfs.ShelfEntity
import com.retro99.games.data.local.model.toDomainModel
import com.retro99.games.data.local.model.toLocalModel
import com.retro99.games.data.remote.model.toDomainModel
import com.retro99.shelfs.data.remote.model.ShelfDto
import com.retro99.shelfs.domain.model.ShelfDomainModel

data class ShelfLocalModel(
    override val id: String,
    override val name: String,
    override val games: List<Int>,
) : ShelfEntity

fun ShelfDto.toLocalModel() = ShelfLocalModel(
    id = id,
    name = name,
    games = games.map { it.id },
)

fun List<ShelfDto>.toLocalModel() = map(ShelfDto::toLocalModel)