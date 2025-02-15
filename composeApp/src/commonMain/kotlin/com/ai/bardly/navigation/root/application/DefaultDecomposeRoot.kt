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
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(ActivityScope::class)
@ContributesBinding(ActivityScope::class, boundType = DecomposeRoot::class)
class DefaultDecomposeRoot(
    componentContext: ComponentContext,
    private val gamesRepository: GamesRepository,
    private val getRecentChatsUseCase: GetRecentChatsUseCase,
    private val analytics: Analytics,
    private val chatsRepository: ChatsRepository,
) : DecomposeRoot, ComponentContext by componentContext, BackHandlerOwner {

    private val navigation = StackNavigation<DecomposeRoot.RootConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = DecomposeRoot.RootConfig.serializer(),
        initialStack = { listOf(DecomposeRoot.RootConfig.Main) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    private fun childFactory(
        screenConfig: DecomposeRoot.RootConfig,
        componentContext: ComponentContext
    ): DecomposeRoot.ApplicationChild = when (screenConfig) {
        DecomposeRoot.RootConfig.Main -> DecomposeRoot.ApplicationChild.Main(
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
