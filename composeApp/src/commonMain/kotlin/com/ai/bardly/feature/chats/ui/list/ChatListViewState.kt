package com.ai.bardly.feature.chats.ui.list

import com.ai.bardly.feature.chats.ui.model.RecentMessageUiModel

data class ChatListViewState(
    val recentChats: List<RecentMessageUiModel> = emptyList(),
)