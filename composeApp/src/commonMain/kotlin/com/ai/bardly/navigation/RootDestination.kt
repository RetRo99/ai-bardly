package com.ai.bardly.navigation

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.chats
import ai_bardly.composeapp.generated.resources.games
import ai_bardly.composeapp.generated.resources.home
import ai_bardly.composeapp.generated.resources.ic_chats
import ai_bardly.composeapp.generated.resources.ic_games
import ai_bardly.composeapp.generated.resources.ic_home
import com.ai.bardly.analytics.AnalyticsEvent
import com.ai.bardly.analytics.AnalyticsEventOrigin
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
) : ScreenViewAnalytics() {

    @Serializable
    data object Home : RootDestination(
        title = Res.string.home,
        icon = Res.drawable.ic_home,
    ) {
        override fun logScreenOpen() {
            analytics.log(
                AnalyticsEvent.ScreenOpen(
                    origin = AnalyticsEventOrigin.Home
                )
            )
        }
    }

    @Serializable
    data object GamesList : RootDestination(
        title = Res.string.games,
        icon = Res.drawable.ic_games,
    ) {
        override fun logScreenOpen() {
            analytics.log(
                AnalyticsEvent.ScreenOpen(
                    origin = AnalyticsEventOrigin.GameList
                )
            )
        }
    }

    @Serializable
    data object ChatsList : RootDestination(
        title = Res.string.chats,
        icon = Res.drawable.ic_chats,
    ) {
        override fun logScreenOpen() {
            analytics.log(
                AnalyticsEvent.ScreenOpen(
                    origin = AnalyticsEventOrigin.RecentChats
                )
            )
        }
    }

    companion object {
        val entries: List<RootDestination> = listOf(Home, GamesList, ChatsList)
    }
}
