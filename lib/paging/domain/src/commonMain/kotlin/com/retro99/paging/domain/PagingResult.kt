package com.retro99.paging.domain

data class PagingResult<ITEM : PagingItem>(
    val items: List<ITEM>,
    val prevKey: Int?,
    val nextKey: Int?,
)
