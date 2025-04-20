package com.retro99.shelfs.domain.model

import com.retro99.paging.domain.PagingItem

data class ShelfDomainModel(
    override val id: String,
) : PagingItem