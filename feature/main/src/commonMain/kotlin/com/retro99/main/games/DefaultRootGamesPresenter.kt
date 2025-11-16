package com.retro99.main.games

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.bardly.games.ui.details.GameDetailsRootPresenterFactory
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
    private val gameDetailsRootPresenterFactory: GameDetailsRootPresenterFactory,
    private val gamesListComponentFactory: GamesListComponentFactory,
) : BasePresenterImpl<RootGamesViewState, RootGamesIntent>(componentContext), RootGamesPresenter {

    override val gamesListComponent = gamesListComponentFactory(
        componentContext,
        { _, _ -> },
        ::openGameDetails,
    )

    private val navigation = SlotNavigation<RootGamesPresenter.Config>()

    override val childSlot = childSlot(
        source = navigation,
        serializer = RootGamesPresenter.Config.serializer(),
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    init {
        // Initialize with GamesList
        navigation.activate(RootGamesPresenter.Config.GamesList)
    }

    override val defaultViewState = RootGamesViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onBackClicked() {
        navigation.dismiss()
    }

    private fun openGameDetails(game: GameUiModel) {
        navigation.activate(RootGamesPresenter.Config.RootGameDetails(game))
    }

    override fun handleScreenIntent(intent: RootGamesIntent) {
        // TODO
    }

    private fun childFactory(
        screenConfig: RootGamesPresenter.Config,
        componentContext: ComponentContext
    ): RootGamesPresenter.Child = when (screenConfig) {
        RootGamesPresenter.Config.GamesList -> RootGamesPresenter.Child.GamesList(
            gamesListComponent
        )

        is RootGamesPresenter.Config.RootGameDetails -> RootGamesPresenter.Child.RootGameDetails(
            gameDetailsRootPresenterFactory(
                componentContext,
                screenConfig.game,
                ::onBackClicked
            )
        )
    }
}
