package com.ai.bardly.navigation.root.main

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.chats
import ai_bardly.composeapp.generated.resources.games
import ai_bardly.composeapp.generated.resources.home
import ai_bardly.composeapp.generated.resources.ic_chats
import ai_bardly.composeapp.generated.resources.ic_games
import ai_bardly.composeapp.generated.resources.ic_home
import com.ai.bardly.feature.chats.ui.recent.RecentChatsComponent
import com.ai.bardly.feature.games.root.RootGamesComponent
import com.ai.bardly.feature.home.root.RootHomeComponent
import com.ai.bardly.navigation.root.RootComponent
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface MainComponent : RootComponent<MainComponent.MainChild> {

    fun navigate(config: MainConfig)
    sealed interface MainChild {
        data class RecentChats(val component: RecentChatsComponent) : MainChild
        data class Home(val component: RootHomeComponent) : MainChild
        data class GameList(val component: RootGamesComponent) : MainChild
    }

    @Serializable
    enum class MainConfig(
        @Contextual
        val title: StringResource,
        @Contextual
        val icon: DrawableResource,
    ) {
        GameList(
            title = Res.string.games,
            icon = Res.drawable.ic_games,
        ),
        Home(
            title = Res.string.home,
            icon = Res.drawable.ic_home,
        ),
        RecentChats(
            title = Res.string.chats,
            icon = Res.drawable.ic_chats,
        ),
    }
}
