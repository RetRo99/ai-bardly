package com.ai.bardly.navigation

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.chats
import ai_bardly.composeapp.generated.resources.games
import ai_bardly.composeapp.generated.resources.home
import ai_bardly.composeapp.generated.resources.ic_chats
import ai_bardly.composeapp.generated.resources.ic_games
import ai_bardly.composeapp.generated.resources.ic_home
import com.ai.bardly.feature.games.domain.GamesRepository
import com.ai.bardly.feature.games.ui.list.DefaultGamesListComponent
import com.ai.bardly.feature.games.ui.list.GamesListComponent
import com.ai.bardly.feature.home.ui.DefaultHomeComponent
import com.ai.bardly.feature.home.ui.HomeComponent
import com.ai.bardly.navigation.ApplicationComponent.ApplicationChild
import com.ai.bardly.navigation.MainComponent.MainChild
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface RootComponent<T : Any> : BackHandlerOwner {
    val childStack: Value<ChildStack<*, T>>

    fun onBackClicked()

}

interface ApplicationComponent : RootComponent<ApplicationChild> {
    sealed interface ApplicationChild {
        data class Main(val component: MainComponent) : ApplicationChild
    }

    @Serializable
    sealed class RootConfig {
        @Serializable
        data object Main : RootConfig()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : ApplicationComponent, ComponentContext by componentContext, BackHandlerOwner {

    private val navigation = StackNavigation<ApplicationComponent.RootConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = ApplicationComponent.RootConfig.serializer(),
        initialStack = { listOf(ApplicationComponent.RootConfig.Main) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }


    private fun childFactory(
        screenConfig: ApplicationComponent.RootConfig,
        componentContext: ComponentContext
    ): ApplicationChild = when (screenConfig) {
        ApplicationComponent.RootConfig.Main -> ApplicationChild.Main(
            DefaultMainComponent(
                componentContext
            )
        )
    }
}

interface RecentChatsComponent

class DefaultRecentChatsComponent(
    componentContext: ComponentContext,
) : RecentChatsComponent, ComponentContext by componentContext

interface MainComponent : RootComponent<MainChild> {

    fun navigate(config: MainConfig)
    sealed interface MainChild {
        data class RecentChats(val component: RecentChatsComponent) : MainChild
        data class Home(val component: HomeComponent) : MainChild
        data class GameList(val component: GamesListComponent) : MainChild
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

class DefaultMainComponent(
    componentContext: ComponentContext,
) : MainComponent, ComponentContext by componentContext, KoinComponent {
    val gamesRepository by inject<GamesRepository>()
    private val navigation = StackNavigation<MainComponent.MainConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = MainComponent.MainConfig.serializer(),
        initialStack = { listOf(MainComponent.MainConfig.Home) },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    override fun navigate(config: MainComponent.MainConfig) {
        navigation.pushToFront(config)
    }

    private fun childFactory(
        screenConfig: MainComponent.MainConfig,
        componentContext: ComponentContext
    ): MainChild = when (screenConfig) {
        MainComponent.MainConfig.GameList -> MainChild.GameList(
            DefaultGamesListComponent(
                componentContext,
                gamesRepository,
            )
        )

        MainComponent.MainConfig.Home -> MainChild.Home(
            DefaultHomeComponent(
                componentContext,
                gamesRepository,
            )
        )

        MainComponent.MainConfig.RecentChats -> MainChild.RecentChats(
            DefaultRecentChatsComponent(
                componentContext
            )
        )
    }
}