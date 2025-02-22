package com.ai.bardly.feature.main.chats.ui.recent

import com.retro99.base.ui.BaseScreenIntent

sealed interface RecentChatsIntent : BaseScreenIntent {
    data class RecentChatClicked(
        val gameTitle: String,
        val gameId: Int,
    ) : RecentChatsIntent
}