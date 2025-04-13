package com.retro99.shelfs.data.local.model

import com.retro99.database.api.shelfs.ShelfEntity
import com.retro99.shelfs.data.remote.model.ShelfDto
import com.retro99.shelfs.domain.model.ShelfDomainModel

data class ShelfLocalModel(
    override val id: Int,
) : ShelfEntity

fun ShelfEntity.toDomainModel() = ShelfDomainModel(
    id = id,
)

fun List<ShelfEntity>.toDomainModel() = map(ShelfEntity::toDomainModel)

fun ShelfDto.toLocalModel() = ShelfLocalModel(
    id = id,
)

fun List<ShelfDto>.toLocalModel() = map(ShelfDto::toLocalModel)