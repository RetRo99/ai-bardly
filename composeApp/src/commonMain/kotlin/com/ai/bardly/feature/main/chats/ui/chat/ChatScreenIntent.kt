package com.ai.bardly.feature.main.chats.ui.chat

import com.ai.bardly.base.BaseScreenIntent
import com.ai.bardly.feature.main.chats.ui.model.MessageUiModel

sealed interface ChatScreenIntent : BaseScreenIntent {
    data object NavigateBack : ChatScreenIntent
    data class SendMessage(val messageText: String) : ChatScreenIntent
    data class MessageAnimationDone(val message: MessageUiModel) : ChatScreenIntent
}