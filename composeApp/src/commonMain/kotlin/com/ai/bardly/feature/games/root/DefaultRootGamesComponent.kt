package com.ai.bardly.feature.games.root

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.base.BaseComponentImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.ui.chat.DefaultChatComponent
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.details.DefaultGameDetailsComponent
import com.ai.bardly.feature.games.ui.list.DefaultGamesListComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push

class DefaultRootGamesComponent(
    componentContext: ComponentContext,
    private val gamesRepository: GamesRepository,
    private val chatRepository: ChatsRepository,
    private val analytics: Analytics,
) : BaseComponentImpl<RootGamesViewState, RootGamesIntent>(componentContext), RootGamesComponent {

    private val navigation = StackNavigation<RootGamesComponent.GamesConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootGamesComponent.GamesConfig.serializer(),
        initialStack = { listOf(RootGamesComponent.GamesConfig.GamesList) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override val defaultViewState = RootGamesViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: RootGamesIntent) {
        // TODO
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun childFactory(
        screenConfig: RootGamesComponent.GamesConfig,
        componentContext: ComponentContext
    ): RootGamesComponent.GamesChild = when (screenConfig) {
        RootGamesComponent.GamesConfig.GamesList -> RootGamesComponent.GamesChild.GamesList(
            DefaultGamesListComponent(
                componentContext,
                gamesRepository,
                { title, id -> navigation.push(RootGamesComponent.GamesConfig.Chat(title, id)) },
                { game -> navigation.push(RootGamesComponent.GamesConfig.GameDetails(game)) }
            )
        )

        is RootGamesComponent.GamesConfig.GameDetails -> RootGamesComponent.GamesChild.GameDetails(
            DefaultGameDetailsComponent(
                componentContext,
                screenConfig.game,
                gamesRepository,
                { title, id -> navigation.push(RootGamesComponent.GamesConfig.Chat(title, id)) },
                ::onBackClicked,
            )
        )

        is RootGamesComponent.GamesConfig.Chat -> RootGamesComponent.GamesChild.ChatDetails(
            DefaultChatComponent(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                chatRepository,
                ::onBackClicked,
                analytics,
            )
        )
    }
}