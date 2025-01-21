package com.ai.bardly.feature.chats.ui.details

import com.ai.bardly.base.ScreenIntent
import com.ai.bardly.feature.chats.ui.model.MessageUiModel

sealed interface ChatDetailsIntent : ScreenIntent {
    object NavigateBack : ChatDetailsIntent
    data class SendMessage(val messageText: String) : ChatDetailsIntent
    data class MessageAnimationDone(val message: MessageUiModel) : ChatDetailsIntent
}