package com.retro99.main.games

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.bardly.chats.ui.chat.ChatPresenterFactory
import com.bardly.games.ui.details.GameDetailsPresenterFactory
import com.bardly.games.ui.list.GamesListComponentFactory
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias RootGamesPresenterFactory = (
    ComponentContext,
    openLogin: () -> Unit,
) -> DefaultRootGamesPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = RootGamesPresenter::class)
class DefaultRootGamesPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val openLogin: () -> Unit,
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

    private fun openChat(title: String, id: String) {
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
                openLogin,
            )
        )

        is RootGamesPresenter.Config.Chat -> RootGamesPresenter.Child.Chat(
            chatPresenterFactory(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                ::onBackClicked,
            )
        )
    }
}