package com.retro99.shelfs.domain.model

data class RemoveGameFromShelfDomainModel(
    val shelfId: String,
    val gameId: String,
)