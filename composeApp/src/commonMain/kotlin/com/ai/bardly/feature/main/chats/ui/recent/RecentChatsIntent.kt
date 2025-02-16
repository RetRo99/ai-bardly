package com.ai.bardly.feature.main.chats.ui.recent

import com.ai.bardly.base.ScreenIntent

sealed interface RecentChatsIntent : ScreenIntent {
    data class RecentChatClicked(
        val gameTitle: String,
        val gameId: Int,
    ) : RecentChatsIntent
}