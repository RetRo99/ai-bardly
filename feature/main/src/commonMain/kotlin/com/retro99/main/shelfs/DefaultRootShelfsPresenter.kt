package com.retro99.main.shelfs

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.bardly.chats.ui.chat.ChatPresenterFactory
import com.bardly.games.ui.details.GameDetailsPresenterFactory
import com.bardly.shelfs.ui.details.ShelfDetailsPresenterFactory
import com.bardly.shelfs.ui.list.ShelfsListComponentFactory
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

typealias RootShelfsPresenterFactory = (
    ComponentContext,
    openLogin: () -> Unit,
) -> DefaultRootShelfsPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = RootShelfsPresenter::class)
class DefaultRootShelfsPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val openLogin: () -> Unit,
    private val shelfDetailsPresenterFactory: ShelfDetailsPresenterFactory,
    private val gameDetailsPresenterFactory: GameDetailsPresenterFactory,
    private val chatPresenterFactory: ChatPresenterFactory,
    private val shelfsListComponentFactory: ShelfsListComponentFactory,
) : BasePresenterImpl<RootShelfsViewState, RootShelfsIntent>(componentContext),
    RootShelfsPresenter {

    private val navigation = StackNavigation<RootShelfsPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootShelfsPresenter.Config.serializer(),
        initialStack = { listOf(RootShelfsPresenter.Config.ShelfsList) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override val defaultViewState = RootShelfsViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    override fun onBackClicked() {
        navigation.pop()
    }


    private fun openShelfDetails(shelf: ShelfUiModel) {
        navigation.pushNew(RootShelfsPresenter.Config.ShelfDetails(shelf))
    }

    private fun openGameDetails(game: GameUiModel) {
        navigation.pushNew(RootShelfsPresenter.Config.GameDetails(game))
    }

    private fun openChat(title: String, id: String) {
        navigation.pushNew(RootShelfsPresenter.Config.Chat(title, id))
    }

    override fun handleScreenIntent(intent: RootShelfsIntent) {
        // TODO
    }

    private fun childFactory(
        screenConfig: RootShelfsPresenter.Config,
        componentContext: ComponentContext
    ): RootShelfsPresenter.Child = when (screenConfig) {
        RootShelfsPresenter.Config.ShelfsList -> RootShelfsPresenter.Child.ShelfsList(
            shelfsListComponentFactory(
                componentContext,
                ::openShelfDetails,
                ::openGameDetails,
            )
        )

        is RootShelfsPresenter.Config.ShelfDetails -> RootShelfsPresenter.Child.ShelfDetails(
            shelfDetailsPresenterFactory(
                componentContext,
                screenConfig.shelf,
                ::onBackClicked,
                ::openGameDetails,
            )
        )

        is RootShelfsPresenter.Config.GameDetails -> RootShelfsPresenter.Child.GameDetails(
            gameDetailsPresenterFactory(
                componentContext,
                screenConfig.game,
                ::openChat,
                ::onBackClicked,
                openLogin,
            )
        )

        is RootShelfsPresenter.Config.Chat -> RootShelfsPresenter.Child.Chat(
            chatPresenterFactory(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                ::onBackClicked,
            )
        )
    }
}
