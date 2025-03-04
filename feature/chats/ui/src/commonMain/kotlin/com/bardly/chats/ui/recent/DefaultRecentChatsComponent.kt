package com.bardly.chats.ui.recent

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.bardly.chats.ui.model.toUiModel
import com.retro99.analytics.api.Analytics
import com.retro99.analytics.api.AnalyticsEvent
import com.retro99.analytics.api.AnalyticsEventOrigin
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import com.retro99.chats.domain.GetRecentChatsUseCase
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias RecentChatsComponentFactory = (
    componentContext: ComponentContext,
    navigateToChat: (String, Int) -> Unit,
) -> DefaultRecentChatsComponent

@Inject
@ContributesBinding(ActivityScope::class, boundType = RecentChatsComponent::class)
class DefaultRecentChatsComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted private val navigateToChat: (String, Int) -> Unit,
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
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
        launchDataOperation(
            block = { getRecentChatsUseCase() },
        ) { recentChats ->
            updateOrSetSuccess {
                it.copy(recentChats = recentChats.toUiModel())
            }
        }
    }
}