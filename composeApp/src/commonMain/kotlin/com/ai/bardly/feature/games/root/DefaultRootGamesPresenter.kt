package com.ai.bardly.feature.games.root

import com.ai.bardly.analytics.Analytics
import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.chats.domain.ChatsRepository
import com.ai.bardly.feature.chats.ui.chat.DefaultChatComponent
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.details.DefaultGameDetailsComponent
import com.ai.bardly.feature.games.ui.list.DefaultGamesListComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
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
    private val gamesRepository: GamesRepository,
    private val chatRepository: ChatsRepository,
    private val analytics: Analytics,
) : BasePresenterImpl<RootGamesViewState, RootGamesIntent>(componentContext), RootGamesPresenter {

    private val navigation = StackNavigation<RootGamesPresenter.GamesConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootGamesPresenter.GamesConfig.serializer(),
        initialStack = { listOf(RootGamesPresenter.GamesConfig.GamesList) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override val defaultViewState = RootGamesViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override suspend fun handleScreenIntent(intent: RootGamesIntent) {
        // TODO
    }

    @OptIn(DelicateDecomposeApi::class)
    private fun childFactory(
        screenConfig: RootGamesPresenter.GamesConfig,
        componentContext: ComponentContext
    ): RootGamesPresenter.GamesChild = when (screenConfig) {
        RootGamesPresenter.GamesConfig.GamesList -> RootGamesPresenter.GamesChild.GamesList(
            DefaultGamesListComponent(
                componentContext,
                gamesRepository,
                { title, id -> navigation.push(RootGamesPresenter.GamesConfig.Chat(title, id)) },
                { game -> navigation.push(RootGamesPresenter.GamesConfig.GameDetails(game)) },
                analytics
            )
        )

        is RootGamesPresenter.GamesConfig.GameDetails -> RootGamesPresenter.GamesChild.GameDetails(
            DefaultGameDetailsComponent(
                componentContext,
                screenConfig.game,
                gamesRepository,
                { title, id -> navigation.push(RootGamesPresenter.GamesConfig.Chat(title, id)) },
                ::onBackClicked,
                analytics,
            )
        )

        is RootGamesPresenter.GamesConfig.Chat -> RootGamesPresenter.GamesChild.ChatDetails(
            DefaultChatComponent(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                chatRepository,
                ::onBackClicked,
                analytics,
            )
        )
    }
}