package com.retro99.chats.domain.model

import kotlinx.datetime.LocalDateTime

data class RecentMessageDomainModel(
    val gameId: Int,
    val gameTitle: String,
    val timestamp: LocalDateTime,
    val thumbnail: String,
)