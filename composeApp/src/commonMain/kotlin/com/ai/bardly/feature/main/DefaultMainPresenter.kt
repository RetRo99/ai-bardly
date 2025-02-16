package com.ai.bardly.feature.main

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.main.chats.root.RootRecentPresenterFactory
import com.ai.bardly.feature.main.games.root.RootGamesPresenterFactory
import com.ai.bardly.feature.main.home.root.RootHomePresenterFactory
import com.ai.bardly.navigation.switchTab
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias MainPresenterFactory = (
    ComponentContext,
) -> DefaultMainPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = MainPresenter::class)
class DefaultMainPresenter(
    @Assisted componentContext: ComponentContext,
    private val rootGamesFactory: RootGamesPresenterFactory,
    private val rootHomeFactory: RootHomePresenterFactory,
    private val rootRecentFactory: RootRecentPresenterFactory,
) : MainPresenter, ComponentContext by componentContext {
    private val navigation = StackNavigation<MainPresenter.MainConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = MainPresenter.MainConfig.serializer(),
        initialConfiguration = MainPresenter.MainConfig.Home,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: MainPresenter.MainConfig) {
        navigation.switchTab(config)
    }

    private fun childFactory(
        screenConfig: MainPresenter.MainConfig,
        componentContext: ComponentContext
    ): MainPresenter.MainChild = when (screenConfig) {
        MainPresenter.MainConfig.GameList -> MainPresenter.MainChild.GameList(
            rootGamesFactory(componentContext)
        )

        MainPresenter.MainConfig.Home -> MainPresenter.MainChild.Home(
            rootHomeFactory(componentContext)
        )

        MainPresenter.MainConfig.RecentChats -> MainPresenter.MainChild.RecentChats(
            rootRecentFactory(
                componentContext,
            )
        )
    }
}