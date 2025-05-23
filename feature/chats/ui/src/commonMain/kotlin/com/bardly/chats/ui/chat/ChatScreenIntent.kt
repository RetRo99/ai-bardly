package com.bardly.chats.ui.chat

import com.bardly.chats.ui.model.MessageUiModel
import com.retro99.base.ui.BaseScreenIntent

sealed interface ChatScreenIntent : BaseScreenIntent {
    data object NavigateBack : ChatScreenIntent
    data class SendMessage(val messageText: String) : ChatScreenIntent
    data class MessageAnimationDone(val message: MessageUiModel) : ChatScreenIntent
}