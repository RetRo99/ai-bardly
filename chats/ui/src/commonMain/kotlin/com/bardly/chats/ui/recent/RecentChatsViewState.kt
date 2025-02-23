package com.bardly.chats.ui.recent

import com.bardly.chats.ui.model.RecentMessageUiModel

data class RecentChatsViewState(
    val recentChats: List<RecentMessageUiModel> = emptyList(),
)