package com.ai.bardly.navigation.root.application

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.domain.GetRecentChatsUseCase
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.navigation.root.main.DefaultMainNavigationComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ActivityScope::class)
@ContributesBinding(ActivityScope::class, boundType = RootPresenter::class)
class DefaultRootPresenter(
    componentContext: ComponentContext,
    private val gamesRepository: GamesRepository,
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
    private val analytics: Analytics,
    private val chatsRepository: ChatsRepository,
) : RootPresenter, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootPresenter.RootConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootPresenter.RootConfig.serializer(),
        initialStack = { listOf(RootPresenter.RootConfig.Main) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun childFactory(
        screenConfig: RootPresenter.RootConfig,
        componentContext: ComponentContext
    ): RootPresenter.ApplicationChild = when (screenConfig) {
        RootPresenter.RootConfig.Main -> RootPresenter.ApplicationChild.Main(
            DefaultMainNavigationComponent(
                componentContext,
                gamesRepository = gamesRepository,
                getRecentChatsUseCase = getRecentChatsUseCase,
                analytics = analytics,
                chatsRepository = chatsRepository
            )
        )
    }
}
