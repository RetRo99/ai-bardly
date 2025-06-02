package com.retro99.main.home

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.bardly.chats.ui.chat.ChatPresenterFactory
import com.bardly.games.ui.details.GameDetailsPresenterFactory
import com.bardly.games.ui.model.GameUiModel
import com.bardly.home.ui.HomePresenterFactory
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias RootHomePresenterFactory = (
    ComponentContext,
    openLogin: () -> Unit,
    navigateToRootGames: () -> Unit,
) -> DefaultRootHomePresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = RootHomePresenter::class)
class DefaultRootHomePresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val openLogin: () -> Unit,
    @Assisted private val navigateToRootGames: () -> Unit,
    private val homePresenterFactory: HomePresenterFactory,
    private val gameDetailsPresenterFactory: GameDetailsPresenterFactory,
    private val chatPresenterFactory: ChatPresenterFactory,
) : BasePresenterImpl<RootHomeViewState, RootHomeIntent>(componentContext), RootHomePresenter {

    private val navigation = StackNavigation<RootHomePresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootHomePresenter.Config.serializer(),
        initialStack = { listOf(RootHomePresenter.Config.Home) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override val defaultViewState = RootHomeViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun handleScreenIntent(intent: RootHomeIntent) {
        // TODO
    }

    private fun openChat(title: String, id: String) {
        navigation.pushNew(RootHomePresenter.Config.Chat(title, id))
    }

    private fun openGameDetails(game: GameUiModel) {
        navigation.pushNew(RootHomePresenter.Config.GameDetails(game))
    }

    private fun childFactory(
        screenConfig: RootHomePresenter.Config,
        componentContext: ComponentContext
    ): RootHomePresenter.Child = when (screenConfig) {
        RootHomePresenter.Config.Home -> RootHomePresenter.Child.Home(
            homePresenterFactory(
                componentContext,
                ::openChat,
                ::openGameDetails,
                navigateToRootGames
            )
        )

        is RootHomePresenter.Config.GameDetails -> RootHomePresenter.Child.GameDetails(
            gameDetailsPresenterFactory(
                componentContext,
                screenConfig.game,
                ::openChat,
                ::onBackClicked,
                openLogin,
            )
        )

        is RootHomePresenter.Config.Chat -> RootHomePresenter.Child.Chat(
            chatPresenterFactory(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                ::onBackClicked,
            )
        )
    }
}
