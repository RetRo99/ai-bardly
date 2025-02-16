package com.ai.bardly.feature.main.games.root

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.main.chats.ui.chat.ChatPresenterFactory
import com.ai.bardly.feature.main.games.ui.details.GameDetailsPresenterFactory
import com.ai.bardly.feature.main.games.ui.list.GamesListComponentFactory
import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
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

    override val defaultViewState = RootGamesViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun openChat(title: String, id: Int) {
        navigation.pushNew(RootGamesPresenter.GamesConfig.Chat(title, id))
    }

    private fun openGameDetails(game: GameUiModel) {
        navigation.pushNew(RootGamesPresenter.GamesConfig.GameDetails(game))
    }

    override suspend fun handleScreenIntent(intent: RootGamesIntent) {
        // TODO
    }

    private fun childFactory(
        screenConfig: RootGamesPresenter.GamesConfig,
        componentContext: ComponentContext
    ): RootGamesPresenter.GamesChild = when (screenConfig) {
        RootGamesPresenter.GamesConfig.GamesList -> RootGamesPresenter.GamesChild.GamesList(
            gamesListComponentFactory(
                componentContext,
                ::openChat,
                ::openGameDetails,
            )
        )

        is RootGamesPresenter.GamesConfig.GameDetails -> RootGamesPresenter.GamesChild.GameDetails(
            gameDetailsPresenterFactory(
                componentContext,
                screenConfig.game,
                ::openChat,
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