package com.ai.bardly.navigation.root.main

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.domain.GetRecentChatsUseCase
import com.ai.bardly.feature.chats.ui.root.DefaultRootRecentComponent
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.root.DefaultRootGamesComponent
import com.ai.bardly.feature.home.root.DefaultRootHomeComponent
import com.ai.bardly.navigation.switchTab
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop

class DefaultMainPresenter(
    componentContext: ComponentContext,
    private val gamesRepository: GamesRepository,
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
    private val analytics: Analytics,
    private val chatsRepository: ChatsRepository,
) : MainNavigationComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<MainNavigationComponent.MainConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = MainNavigationComponent.MainConfig.serializer(),
        initialStack = { listOf(MainNavigationComponent.MainConfig.Home) },
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: MainNavigationComponent.MainConfig) {
        navigation.switchTab(config)
    }

    private fun childFactory(
        screenConfig: MainNavigationComponent.MainConfig,
        componentContext: ComponentContext
    ): MainNavigationComponent.MainChild = when (screenConfig) {
        MainNavigationComponent.MainConfig.GameList -> MainNavigationComponent.MainChild.GameList(
            DefaultRootGamesComponent(
                componentContext,
                gamesRepository,
                chatRepository = chatsRepository,
                analytics = analytics,
            )
        )

        MainNavigationComponent.MainConfig.Home -> MainNavigationComponent.MainChild.Home(
            DefaultRootHomeComponent(
                componentContext,
                gamesRepository,
                chatsRepository = chatsRepository,
                analytics = analytics,
            )
        )

        MainNavigationComponent.MainConfig.RecentChats -> MainNavigationComponent.MainChild.RecentChats(
            DefaultRootRecentComponent(
                componentContext,
                recentChatUseCase = getRecentChatsUseCase,
                chatRepository = chatsRepository,
                analytics = analytics,
            )
        )
    }
}