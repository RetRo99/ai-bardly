package com.ai.bardly.feature.main.chats.domain.model

import kotlinx.datetime.LocalDateTime

data class RecentMessageDomainModel(
    val gameId: Int,
    val gameTitle: String,
    val timestamp: LocalDateTime,
    val thumbnail: String,
)