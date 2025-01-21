package com.ai.bardly.feature.chats.ui.details

import androidx.lifecycle.viewModelScope
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.domain.model.MessageType
import com.ai.bardly.feature.chats.ui.model.MessageUiModel
import com.ai.bardly.feature.chats.ui.model.toDomainModel
import com.ai.bardly.feature.games.ui.model.toUiModel
import kotlinx.coroutines.launch

class ChatsDetailsViewModel(
    private val gameTitle: String,
    private val gameId: Int,
    private val chatsRepository: ChatsRepository,
) : BaseViewModel<ChatDetailsViewState, ChatDetailsIntent>() {

    override val defaultViewState = ChatDetailsViewState(
        title = gameTitle,
        gameId = gameId,
        messages = emptyList(),
        isResponding = false
    )

    override suspend fun handleScreenIntent(intent: ChatDetailsIntent) {
        when (intent) {
            ChatDetailsIntent.NavigateBack -> navigateBack()
            is ChatDetailsIntent.MessageAnimationDone -> onMessageAnimationEnded(intent.message)
            is ChatDetailsIntent.SendMessage -> onMessageSendClicked(intent.messageText)
        }
    }

    override val initialState = BaseViewState.Success(defaultViewState)

    init {
        viewModelScope.launch {
            chatsRepository
                .getMessages(gameId)
                .onSuccess { messages ->
                    updateOrSetSuccess {
                        it.copy(
                            messages = messages.map { it.toUiModel(false) }
                        )
                    }
                }
                .onFailure {
                    updateOrSetSuccess {
                        it.copy(
                            messages = emptyList()
                        )
                    }
                }
        }
    }

    private suspend fun onMessageSendClicked(messageText: String) {
        val message = displayAndGetMessage(
            messageText = messageText,
            id = gameId,
        )
        chatsRepository
            .getAnswerFor(message.toDomainModel())
            .onSuccess { answer ->
                displayMessage(answer.toUiModel(true))
            }.onFailure {
                updateOrSetSuccess {
                    it.copy(
                        isResponding = false
                    )
                }
            }
    }

    private fun onMessageAnimationEnded(message: MessageUiModel) {
        updateOrSetSuccess {
            it.copy(
                messages = it.messages.map {
                    if (it.timestamp == message.timestamp) {
                        it.copy(animateText = false)
                    } else {
                        it
                    }
                }
            )
        }
    }

    private fun displayAndGetMessage(
        messageText: String,
        id: Int,
    ): MessageUiModel {
        val message = MessageUiModel(messageText, MessageType.User, id, gameTitle)
        displayMessage(message)
        return message
    }

    private fun displayMessage(
        message: MessageUiModel
    ) {
        updateOrSetSuccess {
            it.copy(
                messages = listOf(message) + it.messages,
                isResponding = message.isUserMessage,
            )
        }
    }
}