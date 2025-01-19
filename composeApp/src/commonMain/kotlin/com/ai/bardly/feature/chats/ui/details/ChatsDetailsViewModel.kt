package com.ai.bardly.feature.chats.ui.details

import androidx.lifecycle.viewModelScope
import com.ai.bardly.MessageUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.domain.model.MessageType
import com.ai.bardly.toDomainModel
import com.ai.bardly.toUiModel
import kotlinx.coroutines.launch

class ChatsDetailsViewModel(
    private val gameTitle: String,
    private val gameId: Int,
    private val chatsRepository: ChatsRepository,
) : BaseViewModel<ChatDetailsViewState>() {

    override val defaultScreenData = ChatDetailsViewState(
        title = gameTitle,
        gameId = gameId,
        messages = emptyList(),
        isResponding = false
    )

    override val initialState = BaseViewState.Success(defaultScreenData)

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

    fun onBackClick() {
        navigateBack()
    }

    fun onMessageSendClicked(messageText: String) {
        val message = displayAndGetMessage(
            messageText = messageText,
            id = gameId,
        )
        viewModelScope.launch {
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
    }

    fun onMessageAnimationEnded(message: MessageUiModel) {
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