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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import bardlyLightColors
import com.ai.bardly.feature.chats.ui.chat.ChatScreen
import com.ai.bardly.feature.chats.ui.recent.ChatsListScreen
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.games.ui.list.GamesListScreen
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.feature.home.ui.HomeScreen
import com.ai.bardly.navigation.GeneralDestination
import com.ai.bardly.navigation.GeneralDestination.Chat
import com.ai.bardly.navigation.GeneralDestination.GameDetail
import com.ai.bardly.navigation.NavigationManager
import com.ai.bardly.navigation.RootDestination
import com.ai.bardly.util.LocalScreenTransitionScope
import com.ai.bardly.util.baseComposable
import com.ai.bardly.util.serializableNavType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
    MaterialTheme(
        colorScheme = bardlyLightColors
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            val navigationManager = koinInject<NavigationManager>()
            LaunchedEffect(Unit) {
                navigationManager.destinations.collect { destination ->
                    when (destination) {
                        is GeneralDestination.Back -> {
                            navController.navigateUp()
                        }

                        else -> {
                            navController.navigate(destination)
                        }
                    }
                }
            }

            var bottomBarVisible by remember { mutableStateOf(true) }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomBarVisible = RootDestination.entries.any { destination ->
                currentDestination?.hierarchy?.any {
                    it.hasRoute(
                        destination::class.qualifiedName.orEmpty(),
                        arguments = null
                    )
                } == true
            }

            Scaffold(
                bottomBar = {
                    AnimatedVisibility(
                        bottomBarVisible,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        BottomBar(navController)
                    }
                }
            ) { innerPadding ->
                SharedTransitionLayout(
                    modifier = Modifier.padding(innerPadding),
                ) {
                    CompositionLocalProvider(
                        LocalScreenTransitionScope provides this,
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = RootDestination.Home,
                        ) {
                            baseComposable<RootDestination.Home> {
                                HomeScreen()
                            }
                            baseComposable<RootDestination.GamesList> {
                                GamesListScreen(
                                )
                            }
                            baseComposable<RootDestination.ChatsList> {
                                ChatsListScreen()
                            }
                            baseComposable<Chat> { backStackEntry ->
                                val gameTitle = backStackEntry.toRoute<Chat>().gameTitle
                                val gameId = backStackEntry.toRoute<Chat>().gameId
                                ChatScreen(
                                    gameTitle = gameTitle,
                                    gameId = gameId,
                                )
                            }

                            baseComposable<GameDetail>(
                                typeMap = serializableNavType<GameUiModel>()
                            ) { backStackEntry ->
                                val game = backStackEntry.toRoute<GameDetail>().game
                                GameDetailsScreen(
                                    game = game,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
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
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        RootDestination.entries.forEach { destination ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(
                    destination::class.qualifiedName.orEmpty(),
                    arguments = null
                )
            } == true

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
                        navController.navigate(destination) {
                            destination.logScreenOpen()
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
