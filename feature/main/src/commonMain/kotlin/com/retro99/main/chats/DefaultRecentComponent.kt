package com.retro99.main.chats

import com.ai.bardly.annotations.ActivityScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.bardly.chats.ui.chat.ChatPresenterFactory
import com.bardly.chats.ui.recent.RecentChatsComponentFactory
import com.retro99.base.ui.BasePresenterImpl
import com.retro99.base.ui.BaseViewState
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

internal typealias RootRecentPresenterFactory = (
    ComponentContext,
) -> DefaultRootRecentPresenter

@Inject
@ContributesBinding(ActivityScope::class, boundType = RootRecentPresenter::class)
class DefaultRootRecentPresenter(
    @Assisted componentContext: ComponentContext,
    private val chatPresenterFactory: ChatPresenterFactory,
    private val recentChatsComponentFactory: RecentChatsComponentFactory,
) : BasePresenterImpl<RootRecentViewState, RootRecentIntent>(componentContext),
    RootRecentPresenter {

    private val navigation = StackNavigation<RootRecentPresenter.Config>()

    override val childStack = childStack(
        source = navigation,
        serializer = RootRecentPresenter.Config.serializer(),
        initialStack = { listOf(RootRecentPresenter.Config.RecentChats) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override val defaultViewState = RootRecentViewState

    override val initialState = BaseViewState.Success(defaultViewState)

    private fun openChat(title: String, id: Int) {
        navigation.pushNew(RootRecentPresenter.Config.Chat(title, id))
    }

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun handleScreenIntent(intent: RootRecentIntent) {
        // TODO
    }

    private fun childFactory(
        screenConfig: RootRecentPresenter.Config,
        componentContext: ComponentContext
    ): RootRecentPresenter.Child = when (screenConfig) {
        RootRecentPresenter.Config.RecentChats -> RootRecentPresenter.Child.RecentChats(
            recentChatsComponentFactory(
                componentContext,
                ::openChat,
            )
        )

        is RootRecentPresenter.Config.Chat -> RootRecentPresenter.Child.Chat(
            chatPresenterFactory(
                componentContext,
                screenConfig.title,
                screenConfig.id,
                ::onBackClicked,
            )
        )
    }
}