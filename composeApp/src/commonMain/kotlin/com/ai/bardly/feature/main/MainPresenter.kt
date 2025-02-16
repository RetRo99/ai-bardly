package com.ai.bardly.feature.main

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.chats
import ai_bardly.composeapp.generated.resources.games
import ai_bardly.composeapp.generated.resources.home
import ai_bardly.composeapp.generated.resources.ic_chats
import ai_bardly.composeapp.generated.resources.ic_games
import ai_bardly.composeapp.generated.resources.ic_home
import com.ai.bardly.feature.main.chats.ui.root.RootRecentPresenter
import com.ai.bardly.feature.main.games.root.RootGamesPresenter
import com.ai.bardly.feature.main.home.root.RootHomePresnter
import com.ai.bardly.navigation.root.RootDecomposeComponent
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface MainPresenter :
    RootDecomposeComponent<MainPresenter.MainChild, MainPresenter.MainConfig> {

    fun navigate(config: MainConfig)
    sealed interface MainChild {
        data class RecentChats(val component: RootRecentPresenter) : MainChild
        data class Home(val component: RootHomePresnter) : MainChild
        data class GameList(val component: RootGamesPresenter) : MainChild
    }

    @Serializable
    sealed class MainConfig(
        @Contextual
        val title: StringResource,
        @Contextual
        val icon: DrawableResource,
    ) {
        @Serializable
        data object GameList : MainConfig(
            title = Res.string.games,
            icon = Res.drawable.ic_games,
        )

        @Serializable
        data object Home : MainConfig(
            title = Res.string.home,
            icon = Res.drawable.ic_home,
        )

        @Serializable
        data object RecentChats : MainConfig(
            title = Res.string.chats,
            icon = Res.drawable.ic_chats,
        )
    }

    companion object {
        val rootItems: List<MainConfig> = listOf(
            MainConfig.GameList,
            MainConfig.Home,
            MainConfig.RecentChats,
        )
    }
}