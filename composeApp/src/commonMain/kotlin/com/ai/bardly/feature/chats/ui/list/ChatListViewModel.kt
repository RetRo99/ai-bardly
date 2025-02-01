package com.ai.bardly.feature.chats.ui.list

import androidx.lifecycle.viewModelScope
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.GetRecentChatsUseCase
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
) : BaseViewModel<ChatListViewState, ChatListIntent>() {
    override val defaultViewState = ChatListViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: ChatListIntent) {
        when (intent) {
            is ChatListIntent.RecentChatClicked -> {
            }
        }
    }

    override fun onScreenDisplayed() {
        viewModelScope.launch {

        }
    }
}