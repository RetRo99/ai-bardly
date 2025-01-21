package com.ai.bardly.feature.chats.ui.list

import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState

class ChatListViewModel : BaseViewModel<Unit, ChatListIntent>() {
    override val defaultViewState = Unit

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: ChatListIntent) {
        TODO("Not yet implemented")
    }
}