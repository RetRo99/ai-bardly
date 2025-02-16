package com.ai.bardly

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import bardlyLightColors
import com.ai.bardly.feature.chats.ui.root.RootRecentScreen
import com.ai.bardly.feature.games.root.RootGamesScreen
import com.ai.bardly.feature.home.root.RootHomeScreen
import com.ai.bardly.feature.main.MainPresenter
import com.ai.bardly.navigation.root.application.RootPresenter
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

typealias App = @Composable () -> Unit

@OptIn(ExperimentalDecomposeApi::class)
@Inject
@Composable
fun App(
    applicationComponent: RootPresenter,
) {

    MaterialTheme(
        colorScheme = bardlyLightColors
    ) {
        ChildStack(
            stack = applicationComponent.childStack,
        ) {
            when (val child = it.instance) {
                is RootPresenter.ApplicationChild.Main -> {
                    MainScreen(child.component)
                }
            }
        }
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
private fun MainScreen(component: MainPresenter) {
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
                animation = stackAnimation(fade()),
            ) {
                when (val child = it.instance) {
                    is MainPresenter.MainChild.GameList -> RootGamesScreen(child.component)
                    is MainPresenter.MainChild.Home -> RootHomeScreen(child.component)
                    is MainPresenter.MainChild.RecentChats -> RootRecentScreen(child.component)
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    currentlyActiveChild: MainPresenter.MainChild,
    onNavigationClick: (MainPresenter.MainConfig) -> Unit
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
        MainPresenter.rootItems.forEach { destination ->
            val isSelected = when (currentlyActiveChild) {
                is MainPresenter.MainChild.Home -> destination == MainPresenter.MainConfig.Home
                is MainPresenter.MainChild.GameList -> destination == MainPresenter.MainConfig.GameList
                is MainPresenter.MainChild.RecentChats -> destination == MainPresenter.MainConfig.RecentChats
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
