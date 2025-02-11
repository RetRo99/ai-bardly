package com.ai.bardly

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import bardlyLightColors
import com.ai.bardly.feature.chats.ui.recent.RecentChatsScreen
import com.ai.bardly.feature.games.ui.list.GamesListScreen
import com.ai.bardly.feature.home.ui.HomeScreen
import com.ai.bardly.navigation.ApplicationComponent
import com.ai.bardly.navigation.MainComponent
import com.ai.bardly.util.LocalScreenAnimationScope
import com.ai.bardly.util.LocalScreenTransitionScope
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun App(
    applicationComponent: ApplicationComponent,
) {

    MaterialTheme(
        colorScheme = bardlyLightColors
    ) {
        ChildStack(
            stack = applicationComponent.childStack,
        ) {
            when (val child = it.instance) {
                is ApplicationComponent.ApplicationChild.Main -> {
                    MainContent(child.component)
                }
            }
        }
    }
}

@OptIn(ExperimentalDecomposeApi::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun MainContent(component: MainComponent) {
    Surface {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    true,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    val stack by component.childStack.subscribeAsState()
                    BottomBar(
                        currentlyActiveChild = stack.active.instance,
                        onNavigationClick = { destination ->
                            component.navigate(destination)
                        }
                    )
                }
            }
        ) { innerPadding ->
            ChildStack(
                modifier = Modifier.padding(innerPadding),
                stack = component.childStack,
            ) {
                SharedTransitionLayout {
                    CompositionLocalProvider(
                        LocalScreenTransitionScope provides this,
                        LocalScreenAnimationScope provides this@ChildStack,
                    ) {
                        when (val child = it.instance) {
                            is MainComponent.MainChild.GameList -> GamesListScreen(child.component)
                            is MainComponent.MainChild.Home -> HomeScreen()
                            is MainComponent.MainChild.RecentChats -> RecentChatsScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    currentlyActiveChild: MainComponent.MainChild,
    onNavigationClick: (MainComponent.MainConfig) -> Unit
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    NavigationBar(
        modifier = Modifier.drawBehind {
            val strokeWidth = 2.dp.toPx()
            drawLine(
                color = outlineColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = strokeWidth
            )
        },
        tonalElevation = 8.dp,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        MainComponent.MainConfig.entries.forEach { destination ->
            val isSelected = when (currentlyActiveChild) {
                is MainComponent.MainChild.Home -> destination == MainComponent.MainConfig.Home
                is MainComponent.MainChild.GameList -> destination == MainComponent.MainConfig.GameList
                is MainComponent.MainChild.RecentChats -> destination == MainComponent.MainConfig.RecentChats
            }
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = null,
                        modifier = Modifier.alpha(if (isSelected) 1f else 0.5f)
                    )
                },
                label = {
                    Text(
                        text = stringResource(destination.title),
                        modifier = Modifier.alpha(if (isSelected) 1f else 0.5f)
                    )
                },
                selected = false, // Always false otherwise there is overlay
                onClick = {
                    if (!isSelected) {
                        onNavigationClick(destination)
                    }
                }
            )
        }
    }
}
