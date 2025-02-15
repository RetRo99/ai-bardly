package com.ai.bardly.navigation.root.main

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.chats.ui.root.RootRecentPresenterFactory
import com.ai.bardly.feature.games.root.RootGamesPresenterFactory
import com.ai.bardly.feature.home.root.RootHomePresenterFactory
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
        initialStack = { listOf(MainPresenter.MainConfig.Home) },
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