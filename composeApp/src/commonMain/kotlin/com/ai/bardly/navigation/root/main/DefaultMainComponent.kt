package com.ai.bardly.navigation.root.main

import com.ai.bardly.feature.chats.domain.GetRecentChatsUseCase
import com.ai.bardly.feature.chats.ui.recent.DefaultRecentChatsComponent
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.root.DefaultRootGamesComponent
import com.ai.bardly.feature.home.root.DefaultRootHomeComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultMainComponent(
    componentContext: ComponentContext,
) : MainComponent, ComponentContext by componentContext, KoinComponent {
    val gamesRepository by inject<GamesRepository>()
    val recentChatUseCase by inject<GetRecentChatsUseCase>()
    private val navigation = StackNavigation<MainComponent.MainConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = MainComponent.MainConfig.serializer(),
        initialStack = { listOf(MainComponent.MainConfig.Home) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: MainComponent.MainConfig) {
        navigation.pushToFront(config)
    }

    private fun childFactory(
        screenConfig: MainComponent.MainConfig,
        componentContext: ComponentContext
    ): MainComponent.MainChild = when (screenConfig) {
        MainComponent.MainConfig.GameList -> MainComponent.MainChild.GameList(
            DefaultRootGamesComponent(
                componentContext,
                gamesRepository,
            )
        )

        MainComponent.MainConfig.Home -> MainComponent.MainChild.Home(
            DefaultRootHomeComponent(
                componentContext,
                gamesRepository,
            )
        )

        MainComponent.MainConfig.RecentChats -> MainComponent.MainChild.RecentChats(
            DefaultRecentChatsComponent(
                componentContext,
                recentChatUseCase,
                // TODO navigate to chat
                { title, Id -> },
            )
        )
    }
}