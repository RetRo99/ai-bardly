package com.ai.bardly.feature.chats.ui.chat

import com.ai.bardly.base.ScreenIntent
import com.ai.bardly.feature.chats.ui.model.MessageUiModel

sealed interface ChatScreenIntent : ScreenIntent {
    data object NavigateBack : ChatScreenIntent
    data class SendMessage(val messageText: String) : ChatScreenIntent
    data class MessageAnimationDone(val message: MessageUiModel) : ChatScreenIntent
}