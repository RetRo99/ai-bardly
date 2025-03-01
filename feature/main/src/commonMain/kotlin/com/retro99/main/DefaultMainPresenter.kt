package com.retro99.main

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.retro99.base.ui.decompose.switchTab
import com.retro99.main.chats.RootRecentPresenterFactory
import com.retro99.main.games.RootGamesPresenterFactory
import com.retro99.main.home.RootHomePresenterFactory
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias MainPresenterFactory = (
    ComponentContext,
    openLogin: () -> Unit,
) -> DefaultMainPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = MainPresenter::class)
class DefaultMainPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val openLogin: () -> Unit,
    private val rootGamesFactory: RootGamesPresenterFactory,
    private val rootHomeFactory: RootHomePresenterFactory,
    private val rootRecentFactory: RootRecentPresenterFactory,
) : MainPresenter, ComponentContext by componentContext {
    private val navigation = StackNavigation<MainPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = MainPresenter.Config.serializer(),
        initialConfiguration = MainPresenter.Config.Home,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: MainPresenter.Config) {
        navigation.switchTab(config)
    }

    private fun childFactory(
        screenConfig: MainPresenter.Config,
        componentContext: ComponentContext
    ): MainPresenter.Child = when (screenConfig) {
        MainPresenter.Config.GameList -> MainPresenter.Child.GameList(
            rootGamesFactory(
                componentContext,
                openLogin,
            )
        )

        MainPresenter.Config.Home -> MainPresenter.Child.Home(
            rootHomeFactory(
                componentContext,
                openLogin,
            )
        )

        MainPresenter.Config.RecentChats -> MainPresenter.Child.RecentChats(
            rootRecentFactory(
                componentContext,
            )
        )
    }
}