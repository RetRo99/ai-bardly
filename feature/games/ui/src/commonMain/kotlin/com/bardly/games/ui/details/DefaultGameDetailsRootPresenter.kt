package com.bardly.games.ui.details

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias GameDetailsRootPresenterFactory = (
    ComponentContext,
    game: GameUiModel,
    onBackClicked: () -> Unit,
) -> DefaultGameDetailsRootPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = GameDetailsRootPresenter::class)
class DefaultGameDetailsRootPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val game: GameUiModel,
    @Assisted private val onBackClicked: () -> Unit,
    private val gameDetailsPresenterFactory: GameDetailsPresenterFactory,
) : BasePresenterImpl<GameDetailsRootViewState, GameDetailsRootIntent>(componentContext), GameDetailsRootPresenter {

    private val navigation = StackNavigation<GameDetailsRootPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = GameDetailsRootPresenter.Config.serializer(),
        initialStack = { listOf(GameDetailsRootPresenter.Config.GameDetails(game)) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override val defaultViewState = GameDetailsRootViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onBackClicked() {
        onBackClicked.invoke()
    }

    override fun handleScreenIntent(intent: GameDetailsRootIntent) {
        // No specific intents to handle for now
    }

    private fun childFactory(
        screenConfig: GameDetailsRootPresenter.Config,
        componentContext: ComponentContext
    ): GameDetailsRootPresenter.Child = when (screenConfig) {
        is GameDetailsRootPresenter.Config.GameDetails -> GameDetailsRootPresenter.Child.GameDetails(
            gameDetailsPresenterFactory(
                componentContext,
                screenConfig.game,
                ::openChat,
                ::onBackClicked,
                ::openLogin,
            )
        )
    }

    // Placeholder methods for navigation
    private fun openChat(title: String, id: String) {
        // In a real implementation, this would navigate to a chat screen
        // For now, it's a placeholder
    }

    private fun openLogin() {
        // In a real implementation, this would navigate to a login screen
        // For now, it's a placeholder
    }
}
