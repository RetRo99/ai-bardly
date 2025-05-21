package com.bardly.chats.ui.chat

import com.bardly.chats.ui.model.MessageUiModel

data class ChatViewState(
    val title: String,
    val gameId: String,
    val messages: List<MessageUiModel>,
    val isResponding: Boolean
)
