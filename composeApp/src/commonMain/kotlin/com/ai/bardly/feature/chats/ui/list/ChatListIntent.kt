package com.ai.bardly.feature.chats.ui.list

import com.ai.bardly.base.ScreenIntent

sealed interface ChatListIntent : ScreenIntent {
    data class RecentChatClicked(
        val gameTitle: String,
        val gameId: Int,
    ) : ChatListIntent
}