package com.ai.bardly.feature.chats.ui.recent

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.GetRecentChatsUseCase
import com.ai.bardly.feature.chats.ui.model.toUiModel
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch

class DefaultRecentChatsComponent(
    componentContext: ComponentContext,
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
    private val navigateToChat: (String, Int) -> Unit,
    private val analytics: Analytics,
) : BasePresenterImpl<RecentChatsViewState, RecentChatsIntent>(componentContext),
    RecentChatsComponent {
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