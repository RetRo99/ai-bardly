package com.retro99.shelfs.domain.model

data class CreateShelfDomainModel(
    val name: String,
    val description: String? = null,
)