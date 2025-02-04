package com.ai.bardly.feature.chats.ui.list

import androidx.lifecycle.viewModelScope
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.GetRecentChatsUseCase
import com.ai.bardly.feature.chats.ui.model.toUiModel
import com.ai.bardly.navigation.GeneralDestination
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
) : BaseViewModel<ChatListViewState, ChatListIntent>() {
    override val defaultViewState = ChatListViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: ChatListIntent) {
        when (intent) {
            is ChatListIntent.RecentChatClicked -> onRecentChatClicked(intent)
        }
    }

    private fun onRecentChatClicked(intent: ChatListIntent.RecentChatClicked) {
        analytics.log(
            AnalyticsEvent.OpenChat(
                gameTitle = intent.gameTitle,
                origin = AnalyticsEventOrigin.RecentChatList,
            )
        )
        navigateTo(
            GeneralDestination.Chat(
                gameId = intent.gameId,
                gameTitle = intent.gameTitle
            )
        )
    }

    override fun onScreenDisplayed() {
        viewModelScope.launch {
            getRecentChatsUseCase()
                .onSuccess { recentChats ->
                    updateOrSetSuccess {
                        it.copy(recentChats = recentChats.toUiModel())
                    }
                }
                .onFailure {
                    setError(it)
                }
        }
    }
}