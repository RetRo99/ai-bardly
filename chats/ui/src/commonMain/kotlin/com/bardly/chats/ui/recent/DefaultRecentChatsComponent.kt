package com.bardly.chats.ui.recent

import com.arkivanov.decompose.ComponentContext
import com.bardly.chats.ui.model.toUiModel
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.chats.domain.GetRecentChatsUseCase
import kotlinx.coroutines.launch
import retro99.analytics.api.Analytics
import retro99.analytics.api.AnalyticsEvent
import retro99.analytics.api.AnalyticsEventOrigin

class DefaultRecentChatsComponent(
    componentContext: ComponentContext,
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
    private val navigateToChat: (String, Int) -> Unit,
    private val analytics: Analytics,
) : BasePresenterImpl<RecentChatsViewState, RecentChatsIntent>(componentContext),
    RecentChatsComponent {
    override val defaultViewState = RecentChatsViewState()

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun handleScreenIntent(intent: RecentChatsIntent) {
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
        navigateToChat(intent.gameTitle, intent.gameId)
    }

    override fun onResume() {
        scope.launch {
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