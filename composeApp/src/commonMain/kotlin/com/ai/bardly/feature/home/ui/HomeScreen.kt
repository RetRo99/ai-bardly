package com.ai.bardly.feature.home.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher
import com.ai.bardly.feature.games.ui.components.GamesLazyGrid
import com.ai.bardly.feature.games.ui.model.GameUiModel

@Composable
fun HomeScreen(
) {
    BaseScreen<HomeViewModel, HomeViewState, HomeIntent> { viewState, intentDispatcher ->
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
        GreetingSection()
        WhatsNewSection()
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
fun GreetingSection() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Good to see ya' back!",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "On what quest are we taking on today?",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun WhatsNewSection() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "What's new",
            style = MaterialTheme.typography.headlineLarge,
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "*new game available*",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Explore now!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun RecentGamesSection(
    recentGames: List<GameUiModel>,
    onOpenChatClicked: (String, Int) -> Unit,
    onGameClicked: (GameUiModel) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Recent games",
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
