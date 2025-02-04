package com.ai.bardly.feature.chats.ui.recent

import com.ai.bardly.feature.chats.ui.model.RecentMessageUiModel

data class RecentChatsViewState(
    val recentChats: List<RecentMessageUiModel> = emptyList(),
)