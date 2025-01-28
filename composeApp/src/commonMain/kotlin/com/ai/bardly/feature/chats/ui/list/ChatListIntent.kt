package com.ai.bardly.feature.chats.ui.list

import com.ai.bardly.base.ScreenIntent

sealed interface ChatListIntent : ScreenIntent {
    // TODO no login at chat details
    object Logout : ChatListIntent
}