package com.retro99.main

import com.retro99.base.ui.decompose.RootDecomposeComponent
import com.retro99.base.ui.resources.DrawableRes
import com.retro99.main.chats.RootRecentPresenter
import com.retro99.main.games.RootGamesPresenter
import com.retro99.main.home.RootHomePresenter
import com.retro99.main.shelfs.RootShelfsPresenter
import com.retro99.translations.StringRes
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import resources.icons.ic_chats
import resources.icons.ic_games
import resources.icons.ic_home
import resources.icons.ic_shelfs
import resources.translations.chats
import resources.translations.games
import resources.translations.home
import resources.translations.shelfs

interface MainPresenter :
    RootDecomposeComponent<MainPresenter.Child, MainPresenter.Config> {

    fun navigate(config: Config)
    sealed interface Child {
        data class RecentChats(val component: RootRecentPresenter) : Child
        data class Home(val component: RootHomePresenter) : Child
        data class GameList(val component: RootGamesPresenter) : Child
        data class Shelfs(val component: RootShelfsPresenter) : Child
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

        data object Shelfs : Config(
            title = StringRes.shelfs,
            icon = DrawableRes.ic_shelfs,
        )
    }

    companion object {
        val rootItems: List<Config> = listOf(
            Config.GameList,
            Config.Home,
            Config.RecentChats,
            Config.Shelfs,
        )
    }
}