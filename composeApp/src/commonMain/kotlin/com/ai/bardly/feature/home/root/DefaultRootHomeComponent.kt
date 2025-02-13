package com.ai.bardly.feature.home.root

import com.ai.bardly.base.BaseComponentImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.details.DefaultGameDetailsComponent
import com.ai.bardly.feature.home.ui.DefaultHomeComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push

class DefaultRootHomeComponent(
    componentContext: ComponentContext,
    private val gamesRepository: GamesRepository,
) : BaseComponentImpl<RootHomeViewState, RootHomeIntent>(componentContext), RootHomeComponent {

    private val navigation = StackNavigation<RootHomeComponent.HomeConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootHomeComponent.HomeConfig.serializer(),
        initialStack = { listOf(RootHomeComponent.HomeConfig.Home) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override val defaultViewState = RootHomeViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: RootHomeIntent) {
        // TODO
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun childFactory(
        screenConfig: RootHomeComponent.HomeConfig,
        componentContext: ComponentContext
    ): RootHomeComponent.HomeChild = when (screenConfig) {
        RootHomeComponent.HomeConfig.Home -> RootHomeComponent.HomeChild.Home(
            DefaultHomeComponent(
                componentContext,
                gamesRepository,
                // TODO navigate to chat
                { title, Id -> },
                { game -> navigation.push(RootHomeComponent.HomeConfig.GameDetails(game)) }
            )
        )

        is RootHomeComponent.HomeConfig.GameDetails -> RootHomeComponent.HomeChild.GameDetails(
            DefaultGameDetailsComponent(
                componentContext,
                screenConfig.game,
                gamesRepository,
                // TODO navigate to chat
                { title, Id -> },
                ::onBackClicked,
            )
        )

    }
}