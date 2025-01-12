package com.ai.bardly.screens.chats.details

import com.ai.bardly.MessageUiModel
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState

class ChatsDetailsViewModel(
    private val gameTitle: String,
    private val gameId: Int,
) : BaseViewModel<BaseViewState<ChatDetailsViewState>>() {

    override val initialState: BaseViewState<ChatDetailsViewState> = BaseViewState.Loaded(
        ChatDetailsViewState(
            gameTitle,
            gameId,
            listOf(
                MessageUiModel.UserMessage("Hello"),
                MessageUiModel.BardlyMessage("Hi"),
                MessageUiModel.UserMessage("How are you?"),
                MessageUiModel.BardlyMessage("I'm fine, thank you!"),
            )
        )
    )

    fun onBackClick() {
        navigateBack()
    }

    fun onMessageSendClicked(message: String) {
        TODO("Not yet implemented")
    }
}