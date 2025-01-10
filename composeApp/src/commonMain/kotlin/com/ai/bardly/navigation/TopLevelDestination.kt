package com.ai.bardly.navigation

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.chats
import ai_bardly.composeapp.generated.resources.games
import ai_bardly.composeapp.generated.resources.home
import ai_bardly.composeapp.generated.resources.ic_chats
import ai_bardly.composeapp.generated.resources.ic_games
import ai_bardly.composeapp.generated.resources.ic_home
import androidx.compose.runtime.Composable
import com.ai.bardly.screens.chats.ChatsScreen
import com.ai.bardly.screens.games.list.GamesScreen
import com.ai.bardly.screens.home.HomeScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class TopLevelDestination(
    val route: String,
    val title: StringResource,
    val icon: DrawableResource,
    val screen: @Composable () -> Unit
)  {
    Home(
        route = "home",
        title = Res.string.home,
        icon = Res.drawable.ic_home,
        screen = { HomeScreen() }
    ),
    Games(
        route = "games",
        title = Res.string.games,
        icon = Res.drawable.ic_games,
        screen = { GamesScreen() }
    ),
    Chats(
        route = "chats",
        title = Res.string.chats,
        icon = Res.drawable.ic_chats,
        screen = {  ChatsScreen() }
    );

    companion object {
        fun fromRoute(route: String?): TopLevelDestination? {
            return entries.find { it.route == route }
        }
    }
}
