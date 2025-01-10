package com.ai.bardly

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ai.bardly.analytics.Analytics
import com.ai.bardly.navigation.GameDetail
import com.ai.bardly.navigation.NavigationManager
import com.ai.bardly.navigation.RootDestination
import com.ai.bardly.screens.chats.ChatsListScreen
import com.ai.bardly.screens.games.details.GameDetailsScreen
import com.ai.bardly.screens.games.list.GamesListScreen
import com.ai.bardly.screens.home.HomeScreen
import com.ai.bardly.util.serializableType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import kotlin.reflect.typeOf

@Composable
fun App() {
    MaterialTheme(
        colorScheme = lightColorScheme()
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            val navigationManager = koinInject<NavigationManager>()
            ScreenAnalytics(navController)

            LaunchedEffect(Unit) {
                navigationManager.destinations.collect { destination ->
                    navController.navigate(destination)
                }
            }
            Scaffold(
                bottomBar = {
                    BottomBar(navController)
                }
            ) { innerPadding ->
                NavHost(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    startDestination = RootDestination.Home,
                ) {

                    composable<RootDestination.Home> {
                        HomeScreen()
                    }
                    composable<RootDestination.GamesList> {
                        GamesListScreen()
                    }
                    composable<RootDestination.ChatsList> {
                        ChatsListScreen()
                    }

                    composable<GameDetail>(
                        typeMap = mapOf(typeOf<GameUiModel>() to serializableType<GameUiModel>())
                    ) { backStackEntry ->
                        val game = backStackEntry.toRoute<GameDetail>().game
                        GameDetailsScreen(game)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    NavigationBar {
        val currentDestination = null
        // TODO(SELECTED DESTINATION)
//            RootDestination.fromRoute(navController.currentBackStackEntryAsState().value?.destination?.route)

        RootDestination.entries.forEach { destination ->
            val isSelected = currentDestination == destination
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
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(destination) {
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

@Composable
private fun ScreenAnalytics(navController: NavHostController) {
    val analyticsManager = koinInject<Analytics>()
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { navBackStackEntry ->
            logScreenView(analyticsManager, navBackStackEntry.destination)
        }
    }
}

private fun logScreenView(analytics: Analytics, navDestination: NavDestination) {
    navDestination.route?.let { analytics.log("screen_view", "screen_details", it) }
}
