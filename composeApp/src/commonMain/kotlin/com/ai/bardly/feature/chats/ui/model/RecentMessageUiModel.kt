package com.ai.bardly.feature.chats.ui.model

import kotlinx.datetime.LocalDateTime

data class RecentMessageUiModel(
    val gameId: Int,
    val gameTitle: String,
    val timestamp: LocalDateTime,
    val thumbnail: String?,
)