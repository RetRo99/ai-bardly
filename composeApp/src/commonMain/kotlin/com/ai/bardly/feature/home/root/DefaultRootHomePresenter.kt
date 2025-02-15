package com.ai.bardly.feature.home.root

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.ui.chat.DefaultChatComponent
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.details.DefaultGameDetailsPresenter
import com.ai.bardly.feature.home.ui.HomePresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias RootHomePresenterFactory = (
    ComponentContext,
) -> DefaultRootHomePresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = RootHomePresnter::class)
class DefaultRootHomePresenter(
    @Assisted componentContext: ComponentContext,
    private val homePresenterFactory: HomePresenterFactory,
    private val gamesRepository: GamesRepository,
    private val chatsRepository: ChatsRepository,
    private val analytics: Analytics,
) : BasePresenterImpl<RootHomeViewState, RootHomeIntent>(componentContext), RootHomePresnter {

    private val navigation = StackNavigation<RootHomePresnter.HomeConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootHomePresnter.HomeConfig.serializer(),
        initialStack = { listOf(RootHomePresnter.HomeConfig.Home) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override val defaultViewState = RootHomeViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: RootHomeIntent) {
        // TODO
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun childFactory(
        screenConfig: RootHomePresnter.HomeConfig,
        componentContext: ComponentContext
    ): RootHomePresnter.HomeChild = when (screenConfig) {
        RootHomePresnter.HomeConfig.Home -> RootHomePresnter.HomeChild.Home(
            homePresenterFactory(
                componentContext,
                { title, id -> navigation.push(RootHomePresnter.HomeConfig.Chat(title, id)) },
                { game -> navigation.push(RootHomePresnter.HomeConfig.GameDetails(game)) },
            )
        )

        is RootHomePresnter.HomeConfig.GameDetails -> RootHomePresnter.HomeChild.GameDetails(
            DefaultGameDetailsPresenter(
                componentContext,
                screenConfig.game,
                gamesRepository,
                { title, id -> navigation.push(RootHomePresnter.HomeConfig.Chat(title, id)) },
                ::onBackClicked,
                analytics,
            )
        )

        is RootHomePresnter.HomeConfig.Chat -> RootHomePresnter.HomeChild.Chat(
            DefaultChatComponent(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                chatsRepository,
                ::onBackClicked,
                analytics,
            )
        )
    }
}