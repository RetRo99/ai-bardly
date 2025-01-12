package com.ai.bardly.screens.chats.details

import com.ai.bardly.MessageUiModel

data class ChatDetailsViewState(
    val title: String,
    val gameId: Int,
    val messages: List<MessageUiModel>,
)