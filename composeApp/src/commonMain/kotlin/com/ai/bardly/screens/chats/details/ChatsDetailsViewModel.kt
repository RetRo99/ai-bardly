package com.ai.bardly.screens.chats.details

import androidx.lifecycle.viewModelScope
import com.ai.bardly.MessageUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.base.updateData
import com.ai.bardly.data.chat.ChatsRepository
import com.ai.bardly.data.chat.model.QuestionRequestApiModel
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

    fun onBackClick() {
        navigateBack()
    }

    fun onMessageSendClicked(message: String) {
        addMessage(MessageUiModel.UserMessage(message))
        viewModelScope.launch {
            val answer = chatsRepository.getAnswer(
                QuestionRequestApiModel(
                    question = message,
                    game = gameTitle
                )
            )
            when {
                answer.isSuccess -> {
                    addMessage(MessageUiModel.BardlyMessage(answer.getOrThrow()))
                }

                answer.isFailure -> {
                    // TODO handle error
                }
            }
        }
    }

    private fun addMessage(message: MessageUiModel) {
        updateState { state ->
            state.updateData {
                it.copy(
                    messages = listOf(message) + it.messages,
                    isResponding = message is MessageUiModel.UserMessage,
                )
            }
        }
    }
}