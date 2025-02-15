package com.ai.bardly.feature.chats.ui.root

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.base.BaseComponentImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.domain.GetRecentChatsUseCase
import com.ai.bardly.feature.chats.ui.chat.DefaultChatComponent
import com.ai.bardly.feature.chats.ui.recent.DefaultRecentChatsComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push

class DefaultRootRecentComponent(
    componentContext: ComponentContext,
    private val recentChatUseCase: GetRecentChatsUseCase,
    private val chatRepository: ChatsRepository,
    private val analytics: Analytics,
) : BaseComponentImpl<RootRecentViewState, RootRecentIntent>(componentContext),
    RootRecentComponent {

    private val navigation = StackNavigation<RootRecentComponent.RootRecentConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootRecentComponent.RootRecentConfig.serializer(),
        initialStack = { listOf(RootRecentComponent.RootRecentConfig.RecentChats) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override val defaultViewState = RootRecentViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: RootRecentIntent) {
        // TODO
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun childFactory(
        screenConfig: RootRecentComponent.RootRecentConfig,
        componentContext: ComponentContext
    ): RootRecentComponent.RootRecentChild = when (screenConfig) {
        RootRecentComponent.RootRecentConfig.RecentChats -> RootRecentComponent.RootRecentChild.RecentChats(
            DefaultRecentChatsComponent(
                componentContext,
                recentChatUseCase,
                { title, id ->
                    navigation.push(
                        RootRecentComponent.RootRecentConfig.Chat(
                            title,
                            id
                        )
                    )
                },
                analytics
            )
        )

        is RootRecentComponent.RootRecentConfig.Chat -> RootRecentComponent.RootRecentChild.Chat(
            DefaultChatComponent(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                chatRepository,
                ::onBackClicked,
                analytics
            )
        )
    }
}