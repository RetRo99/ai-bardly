package com.ai.bardly.feature.games.root

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.ui.chat.ChatPresenterFactory
import com.ai.bardly.feature.games.ui.details.GameDetailsPresenterFactory
import com.ai.bardly.feature.games.ui.list.GamesListComponentFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias RootGamesPresenterFactory = (
    ComponentContext,
) -> DefaultRootGamesPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = RootGamesPresenter::class)
class DefaultRootGamesPresenter(
    @Assisted componentContext: ComponentContext,
    private val gameDetailsPresenterFactory: GameDetailsPresenterFactory,
    private val chatPresenterFactory: ChatPresenterFactory,
    private val gamesListComponentFactory: GamesListComponentFactory,
) : BasePresenterImpl<RootGamesViewState, RootGamesIntent>(componentContext), RootGamesPresenter {

    private val navigation = StackNavigation<RootGamesPresenter.GamesConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootGamesPresenter.GamesConfig.serializer(),
        initialStack = { listOf(RootGamesPresenter.GamesConfig.GamesList) },
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
        screenConfig: RootGamesPresenter.GamesConfig,
        componentContext: ComponentContext
    ): RootGamesPresenter.GamesChild = when (screenConfig) {
        RootGamesPresenter.GamesConfig.GamesList -> RootGamesPresenter.GamesChild.GamesList(
            gamesListComponentFactory(
                componentContext,
                { title, id -> navigation.push(RootGamesPresenter.GamesConfig.Chat(title, id)) },
                { game -> navigation.push(RootGamesPresenter.GamesConfig.GameDetails(game)) },
            )
        )

        is RootGamesPresenter.GamesConfig.GameDetails -> RootGamesPresenter.GamesChild.GameDetails(
            gameDetailsPresenterFactory(
                componentContext,
                screenConfig.game,
                { title, id -> navigation.push(RootGamesPresenter.GamesConfig.Chat(title, id)) },
                ::onBackClicked,
            )
        )

        is RootGamesPresenter.GamesConfig.Chat -> RootGamesPresenter.GamesChild.ChatDetails(
            chatPresenterFactory(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                ::onBackClicked,
            )
        )
    }
}