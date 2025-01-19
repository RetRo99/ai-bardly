package com.ai.bardly.feature.chats.ui.details

import com.ai.bardly.MessageUiModel

data class ChatDetailsViewState(
    val title: String,
    val gameId: Int,
    val messages: List<MessageUiModel>,
    val isResponding: Boolean
)