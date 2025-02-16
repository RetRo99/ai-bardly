package com.ai.bardly.feature.main.chats.ui.recent

import com.ai.bardly.feature.main.chats.ui.model.RecentMessageUiModel

data class RecentChatsViewState(
    val recentChats: List<RecentMessageUiModel> = emptyList(),
)