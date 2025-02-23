package com.ai.bardly.feature.main

import com.ai.bardly.feature.main.chats.root.RootRecentPresenter
import com.ai.bardly.feature.main.games.root.RootGamesPresenter
import com.ai.bardly.feature.main.home.root.RootHomePresenter
import com.retro99.base.ui.decompose.RootDecomposeComponent
import com.retro99.base.ui.resources.DrawableRes
import com.retro99.translations.StringRes
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import resources.icons.ic_chats
import resources.icons.ic_games
import resources.icons.ic_home
import resources.translations.chats
import resources.translations.games
import resources.translations.home

interface MainPresenter :
    RootDecomposeComponent<MainPresenter.Child, MainPresenter.Config> {

    fun navigate(config: Config)
    sealed interface Child {
        data class RecentChats(val component: RootRecentPresenter) : Child
        data class Home(val component: RootHomePresenter) : Child
        data class GameList(val component: RootGamesPresenter) : Child
    }

    @Serializable
    sealed class Config(
        @Contextual
        val title: StringResource,
        @Contextual
        val icon: DrawableResource,
    ) {
        @Serializable
        data object GameList : Config(
            title = StringRes.games,
            icon = DrawableRes.ic_games,
        )

        @Serializable
        data object Home : Config(
            title = StringRes.home,
            icon = DrawableRes.ic_home,
        )

        @Serializable
        data object RecentChats : Config(
            title = StringRes.chats,
            icon = DrawableRes.ic_chats,
        )
    }

    companion object {
        val rootItems: List<Config> = listOf(
            Config.GameList,
            Config.Home,
            Config.RecentChats,
        )
    }
}