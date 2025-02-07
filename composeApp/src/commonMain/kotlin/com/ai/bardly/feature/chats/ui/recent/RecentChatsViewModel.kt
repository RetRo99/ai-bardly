package com.ai.bardly.feature.chats.ui.recent

import androidx.lifecycle.viewModelScope
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.base.BaseViewModel
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.GetRecentChatsUseCase
import com.ai.bardly.feature.chats.ui.model.toUiModel
import com.ai.bardly.navigation.GeneralDestination
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RecentChatsViewModel(
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
) : BaseViewModel<RecentChatsViewState, RecentChatsIntent>() {
    override val defaultViewState = RecentChatsViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: RecentChatsIntent) {
        when (intent) {
            is RecentChatsIntent.RecentChatClicked -> onRecentChatClicked(intent)
        }
    }

    private fun onRecentChatClicked(intent: RecentChatsIntent.RecentChatClicked) {
        analytics.log(
            AnalyticsEvent.OpenChat(
                gameTitle = intent.gameTitle,
                origin = AnalyticsEventOrigin.RecentChats,
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