package com.ai.bardly.feature.main.chats.ui.chat

import com.ai.bardly.feature.main.chats.ui.model.MessageUiModel

data class ChatViewState(
    val title: String,
    val gameId: Int,
    val messages: List<MessageUiModel>,
    val isResponding: Boolean
)