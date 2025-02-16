package com.ai.bardly.feature.main.home.root

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.main.chats.ui.chat.ChatPresenterFactory
import com.ai.bardly.feature.main.games.ui.details.GameDetailsPresenterFactory
import com.ai.bardly.feature.main.home.ui.HomePresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias RootHomePresenterFactory = (
    ComponentContext,
) -> DefaultRootHomePresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = RootHomePresnter::class)
class DefaultRootHomePresenter(
    @Assisted componentContext: ComponentContext,
    private val homePresenterFactory: HomePresenterFactory,
    private val gameDetailsPresenterFactory: GameDetailsPresenterFactory,
    private val chatPresenterFactory: ChatPresenterFactory,
) : BasePresenterImpl<RootHomeViewState, RootHomeIntent>(componentContext), RootHomePresnter {

    private val navigation = StackNavigation<RootHomePresnter.HomeConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootHomePresnter.HomeConfig.serializer(),
        initialStack = { listOf(RootHomePresnter.HomeConfig.Home) },
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
        screenConfig: RootHomePresnter.HomeConfig,
        componentContext: ComponentContext
    ): RootHomePresnter.HomeChild = when (screenConfig) {
        RootHomePresnter.HomeConfig.Home -> RootHomePresnter.HomeChild.Home(
            homePresenterFactory(
                componentContext,
                { title, id -> navigation.push(RootHomePresnter.HomeConfig.Chat(title, id)) },
                { game -> navigation.push(RootHomePresnter.HomeConfig.GameDetails(game)) },
            )
        )

        is RootHomePresnter.HomeConfig.GameDetails -> RootHomePresnter.HomeChild.GameDetails(
            gameDetailsPresenterFactory(
                componentContext,
                screenConfig.game,
                { title, id -> navigation.push(RootHomePresnter.HomeConfig.Chat(title, id)) },
                ::onBackClicked,
            )
        )

        is RootHomePresnter.HomeConfig.Chat -> RootHomePresnter.HomeChild.Chat(
            chatPresenterFactory(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                ::onBackClicked,
            )
        )
    }
}