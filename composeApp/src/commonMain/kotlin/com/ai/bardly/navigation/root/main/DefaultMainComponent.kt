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

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val gamesRepository: GamesRepository,
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
    private val analytics: Analytics,
    private val chatsRepository: ChatsRepository,
) : MainComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<MainComponent.MainConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = MainComponent.MainConfig.serializer(),
        initialStack = { listOf(MainComponent.MainConfig.Home) },
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: MainComponent.MainConfig) {
        navigation.switchTab(config)
    }

    private fun childFactory(
        screenConfig: MainComponent.MainConfig,
        componentContext: ComponentContext
    ): MainComponent.MainChild = when (screenConfig) {
        MainComponent.MainConfig.GameList -> MainComponent.MainChild.GameList(
            DefaultRootGamesComponent(
                componentContext,
                gamesRepository,
                chatRepository = chatsRepository,
                analytics = analytics,
            )
        )

        MainComponent.MainConfig.Home -> MainComponent.MainChild.Home(
            DefaultRootHomeComponent(
                componentContext,
                gamesRepository,
                chatsRepository = chatsRepository,
                analytics = analytics,
            )
        )

        MainComponent.MainConfig.RecentChats -> MainComponent.MainChild.RecentChats(
            DefaultRootRecentComponent(
                componentContext,
                recentChatUseCase = getRecentChatsUseCase,
                chatRepository = chatsRepository,
                analytics = analytics,
            )
        )
    }
}