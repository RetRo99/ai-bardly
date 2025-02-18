package com.ai.bardly.feature.main

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.chats
import ai_bardly.composeapp.generated.resources.games
import ai_bardly.composeapp.generated.resources.home
import ai_bardly.composeapp.generated.resources.ic_chats
import ai_bardly.composeapp.generated.resources.ic_games
import ai_bardly.composeapp.generated.resources.ic_home
import com.ai.bardly.decompose.RootDecomposeComponent
import com.ai.bardly.feature.main.chats.root.RootRecentPresenter
import com.ai.bardly.feature.main.games.root.RootGamesPresenter
import com.ai.bardly.feature.main.home.root.RootHomePresenter
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

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
            title = Res.string.games,
            icon = Res.drawable.ic_games,
        )

        @Serializable
        data object Home : Config(
            title = Res.string.home,
            icon = Res.drawable.ic_home,
        )

        @Serializable
        data object RecentChats : Config(
            title = Res.string.chats,
            icon = Res.drawable.ic_chats,
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