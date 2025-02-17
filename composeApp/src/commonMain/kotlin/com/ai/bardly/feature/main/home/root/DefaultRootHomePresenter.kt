package com.ai.bardly.feature.main.home.root

import com.ai.bardly.annotations.ActivityScope
import com.ai.bardly.base.BasePresenterImpl
import com.ai.bardly.base.BaseViewState
import com.ai.bardly.feature.main.chats.ui.chat.ChatPresenterFactory
import com.ai.bardly.feature.main.games.ui.details.GameDetailsPresenterFactory
import com.ai.bardly.feature.main.games.ui.model.GameUiModel
import com.ai.bardly.feature.main.home.ui.HomePresenterFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias RootHomePresenterFactory = (
    ComponentContext,
) -> DefaultRootHomePresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = RootHomePresenter::class)
class DefaultRootHomePresenter(
    @Assisted componentContext: ComponentContext,
    private val homePresenterFactory: HomePresenterFactory,
    private val gameDetailsPresenterFactory: GameDetailsPresenterFactory,
    private val chatPresenterFactory: ChatPresenterFactory,
) : BasePresenterImpl<RootHomeViewState, RootHomeIntent>(componentContext), RootHomePresenter {

    private val navigation = StackNavigation<RootHomePresenter.HomeConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootHomePresenter.HomeConfig.serializer(),
        initialStack = { listOf(RootHomePresenter.HomeConfig.Home) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override val defaultViewState = RootHomeViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onBackClicked() {
        navigation.pop()
    }

    override suspend fun handleScreenIntent(intent: RootHomeIntent) {
        // TODO
    }

    private fun openChat(title: String, id: Int) {
        navigation.pushNew(RootHomePresenter.HomeConfig.Chat(title, id))
    }

    private fun openGameDetails(game: GameUiModel) {
        navigation.pushNew(RootHomePresenter.HomeConfig.GameDetails(game))
    }

    private fun childFactory(
        screenConfig: RootHomePresenter.HomeConfig,
        componentContext: ComponentContext
    ): RootHomePresenter.HomeChild = when (screenConfig) {
        RootHomePresenter.HomeConfig.Home -> RootHomePresenter.HomeChild.Home(
            homePresenterFactory(
                componentContext,
                ::openChat,
                ::openGameDetails,
            )
        )

        is RootHomePresenter.HomeConfig.GameDetails -> RootHomePresenter.HomeChild.GameDetails(
            gameDetailsPresenterFactory(
                componentContext,
                screenConfig.game,
                ::openChat,
                ::onBackClicked,
            )
        )

        is RootHomePresenter.HomeConfig.Chat -> RootHomePresenter.HomeChild.Chat(
            chatPresenterFactory(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                ::onBackClicked,
            )
        )
    }
}