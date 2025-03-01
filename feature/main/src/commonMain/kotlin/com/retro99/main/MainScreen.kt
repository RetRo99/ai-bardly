package com.retro99.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.consumeWindowInsets
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
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.retro99.main.chats.RootRecentScreen
import com.retro99.main.games.RootGamesScreen
import com.retro99.main.home.RootHomeScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainScreen(component: MainPresenter) {
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
                modifier = Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
                stack = component.childStack,
                animation = stackAnimation(fade()),
            ) {
                when (val child = it.instance) {
                    is MainPresenter.Child.GameList -> RootGamesScreen(child.component)
                    is MainPresenter.Child.Home -> RootHomeScreen(child.component)
                    is MainPresenter.Child.RecentChats -> RootRecentScreen(child.component)
                }
            }
        }
    }
}

@Composable
private fun BottomBar(
    currentlyActiveChild: MainPresenter.Child,
    onNavigationClick: (MainPresenter.Config) -> Unit
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
                is MainPresenter.Child.Home -> destination == MainPresenter.Config.Home
                is MainPresenter.Child.GameList -> destination == MainPresenter.Config.GameList
                is MainPresenter.Child.RecentChats -> destination == MainPresenter.Config.RecentChats
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
