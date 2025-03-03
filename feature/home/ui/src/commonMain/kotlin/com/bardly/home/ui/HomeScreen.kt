package com.bardly.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bardly.games.ui.components.GamesLazyGrid
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.stringResource
import resources.translations.home_recent_games

@Composable
fun HomeScreen(
    component: HomePresenter,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        HomeScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun HomeScreenContent(
    viewState: HomeViewState,
    intentDispatcher: IntentDispatcher<HomeIntent>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RecentGamesSection(
            recentGames = viewState.recentGames,
            onOpenChatClicked = { title, id ->
                intentDispatcher(
                    HomeIntent.OpenChatClicked(title, id)
                )
            },
            onGameClicked = {
                intentDispatcher(
                    HomeIntent.OpenGameDetails(it)
                )
            },
        )
    }
}

@Composable
private fun RecentGamesSection(
    recentGames: List<GameUiModel>,
    onOpenChatClicked: (String, Int) -> Unit,
    onGameClicked: (GameUiModel) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(StringRes.home_recent_games),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )

        GamesLazyGrid(
            itemCount = { recentGames.size },
            getItem = { index -> recentGames[index] },
            getKey = { index -> recentGames[index].id },
            onGameClicked = onGameClicked,
            onOpenChatClicked = onOpenChatClicked
        )
    }
}