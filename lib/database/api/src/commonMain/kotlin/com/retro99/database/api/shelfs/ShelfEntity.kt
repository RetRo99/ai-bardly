package com.retro99.database.api.shelfs

import com.retro99.paging.domain.PagingItem

interface ShelfEntity : PagingItem {
    override val id: String
}