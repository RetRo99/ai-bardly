package com.ai.bardly.screens.chats.details

import androidx.lifecycle.viewModelScope
import com.ai.bardly.MessageUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.base.copy
import com.ai.bardly.domain.chats.ChatsRepository
import com.ai.bardly.domain.chats.model.MessageType
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
            val messages = chatsRepository.getMessages(gameTitle)
            when {
                messages.isSuccess -> {
                    updateState {
                        it.copy {
                            it.copy(
                                messages = messages.getOrThrow().map { it.toUiModel() }
                            )
                        }
                    }
                }

                messages.isFailure -> {
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
    }

    fun onBackClick() {
        navigateBack()
    }

    fun onMessageSendClicked(messageText: String) {
        val message = displayAndGetMessage(
            type = MessageType.User,
            messageText = messageText,
            id = gameTitle,
        )
        viewModelScope.launch {
            val answer = chatsRepository.getAnswerFor(message.toDomainModel())
            when {
                answer.isSuccess -> {
                    displayMessage(answer.getOrThrow().toUiModel())
                }

                answer.isFailure -> {
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
    }

    private fun displayAndGetMessage(
        type: MessageType,
        messageText: String,
        id: String,
    ): MessageUiModel {
        val message = MessageUiModel(messageText, type, id)
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