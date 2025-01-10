package com.ai.bardly.navigation

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.chats
import ai_bardly.composeapp.generated.resources.games
import ai_bardly.composeapp.generated.resources.home
import ai_bardly.composeapp.generated.resources.ic_chats
import ai_bardly.composeapp.generated.resources.ic_games
import ai_bardly.composeapp.generated.resources.ic_home
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Serializable
sealed class RootDestination(
    @Contextual
    val title: StringResource,
    @Contextual
    val icon: DrawableResource,
) {

    @Serializable
    data object Home : RootDestination(
        title = Res.string.home,
        icon = Res.drawable.ic_home,
    )

    @Serializable
    data object GamesList : RootDestination(
        title = Res.string.games,
        icon = Res.drawable.ic_games,
    )

    @Serializable
    data object ChatsList : RootDestination(
        title = Res.string.chats,
        icon = Res.drawable.ic_chats,
    )

    companion object {
        val entries = listOf(Home, GamesList, ChatsList)
    }
}
