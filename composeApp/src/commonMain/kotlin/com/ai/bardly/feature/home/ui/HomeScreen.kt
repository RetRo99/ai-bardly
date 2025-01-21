package com.ai.bardly.feature.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.ui.GameCard

@Composable
fun HomeScreen(
) {
    BaseScreen<HomeViewModel, Unit, HomeIntent> { viewState, intentDispatcher ->
        HomeScreenContent(
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun HomeScreenContent(
    intentDispatcher: IntentDispatcher<HomeIntent>,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GreetingSection()
        WhatsNewSection()
        RecentGamesSection(
            onOpenChatClicked = { title, id ->
                intentDispatcher(
                    HomeIntent.OpenChatClicked(title, id)
                )
            }
        )
    }
}

@Composable
fun GreetingSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6F4ACB)
        ),
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
                    style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
                )
                Text(
                    text = "On what quest are we taking on today?",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(
                            alpha = 0.7f
                        )
                    )
                )
            }
        }
    }
}

@Composable
private fun WhatsNewSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "What's new",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
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
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
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
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun RecentGamesSection(onOpenChatClicked: (String, Int) -> Unit) {
    SharedTransitionLayout {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Recent games",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedVisibility(true) {
                    GameCard(
                        GameUiModel(
                            title = "omittam",
                            description = "dolorum",
                            rating = "massa",
                            yearPublished = "torquent",
                            numberOfPlayers = "scripta",
                            playingTime = "periculis",
                            ageRange = "scripta",
                            complexity = "dui",
                            link = "omittam",
                            thumbnail = "dicam",
                            id = 5870
                        ),
                        onGameClicked = {},
                        animatedVisibilityScope = this,
                        onOpenChatClicked = onOpenChatClicked
                    )
                    GameCard(
                        game = GameUiModel(
                            title = "consetetur",
                            description = "ridens",
                            rating = "semper",
                            yearPublished = "suas",
                            numberOfPlayers = "mutat",
                            playingTime = "magna",
                            ageRange = "scelerisque",
                            complexity = "deseruisse",
                            link = "eu",
                            thumbnail = "quot",
                            id = 7759
                        ),
                        onGameClicked = {},
                        animatedVisibilityScope = this,
                        onOpenChatClicked = onOpenChatClicked
                    )

                }
            }
        }
    }
}
