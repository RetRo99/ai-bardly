package com.ai.bardly.feature.main.games.root

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.main.chats.ui.chat.ChatPresenterFactory
import com.ai.bardly.feature.main.games.ui.details.GameDetailsPresenterFactory
import com.ai.bardly.feature.main.games.ui.list.GamesListComponentFactory
import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
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

    private val navigation = StackNavigation<RootGamesPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootGamesPresenter.Config.serializer(),
        initialStack = { listOf(RootGamesPresenter.Config.GamesList) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override val defaultViewState = RootGamesViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun openChat(title: String, id: Int) {
        navigation.pushNew(RootGamesPresenter.Config.Chat(title, id))
    }

    private fun openGameDetails(game: GameUiModel) {
        navigation.pushNew(RootGamesPresenter.Config.GameDetails(game))
    }

    override fun handleScreenIntent(intent: RootGamesIntent) {
        // TODO
    }

    private fun childFactory(
        screenConfig: RootGamesPresenter.Config,
        componentContext: ComponentContext
    ): RootGamesPresenter.Child = when (screenConfig) {
        RootGamesPresenter.Config.GamesList -> RootGamesPresenter.Child.GamesList(
            gamesListComponentFactory(
                componentContext,
                ::openChat,
                ::openGameDetails,
            )
        )

        is RootGamesPresenter.Config.GameDetails -> RootGamesPresenter.Child.GameDetails(
            gameDetailsPresenterFactory(
                componentContext,
                screenConfig.game,
                ::openChat,
                ::onBackClicked,
            )
        )

        is RootGamesPresenter.Config.Chat -> RootGamesPresenter.Child.ChatDetails(
            chatPresenterFactory(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                ::onBackClicked,
            )
        )
    }
}