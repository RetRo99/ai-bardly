package com.ai.bardly.feature.chats.ui.details

import androidx.lifecycle.viewModelScope
import com.ai.bardly.MessageUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.base.copy
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.domain.model.MessageType
import com.ai.bardly.toDomainModel
import com.ai.bardly.toUiModel
import kotlinx.coroutines.launch

class ChatsDetailsViewModel(
    private val gameTitle: String,
    private val gameId: Int,
    private val chatsRepository: ChatsRepository,
) : BaseViewModel<BaseViewState<ChatDetailsViewState>>() {

    override val initialState: BaseViewState<ChatDetailsViewState> = BaseViewState.Loaded(
        ChatDetailsViewState(
            gameTitle,
            gameId,
            emptyList(),
            false
        )
    )

    init {
        viewModelScope.launch {
            chatsRepository
                .getMessages(gameId)
                .onSuccess { messages ->
                    updateState {
                        it.copy {
                            it.copy(
                                messages = messages.map { it.toUiModel() }
                            )
                        }
                    }
                }.onFailure {
                    updateState {
                        it.copy {
                            it.copy(
                                messages = emptyList()
                            )
                        }
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
                    displayMessage(answer.toUiModel())
                }.onFailure {
                    updateState {
                        it.copy {
                            it.copy(
                                isResponding = false
                            )
                        }
                    }

                }
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
        updateState {
            it.copy {
                it.copy(
                    messages = listOf(message) + it.messages,
                    isResponding = message.isUserMessage,
                )
            }
        }
    }
}