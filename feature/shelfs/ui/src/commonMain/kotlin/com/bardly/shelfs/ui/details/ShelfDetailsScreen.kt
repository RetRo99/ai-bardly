package com.bardly.shelfs.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.base.ui.compose.CoilImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun ShelfDetailsScreen(
    component: ShelfDetailsPresenter,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        ShelfsScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun ShelfsScreenContent(
    viewState: ShelfDetailsViewState,
    intentDispatcher: IntentDispatcher<ShelfDetailsIntent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar(
            shelfName = viewState.shelf.name,
            onBackClick = { intentDispatcher(ShelfDetailsIntent.NavigateBack) }
        )

        // Games section
        Text(
            text = "Games (${viewState.shelf.games.size})",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // List of games
        viewState.shelf.games.forEach { game ->
            GameItem(
                game = game,
                onClick = { intentDispatcher(ShelfDetailsIntent.GameClicked(game)) }
            )
        }

        // If no games, show a message
        if (viewState.shelf.games.isEmpty()) {
            Text(
                text = "No games in this shelf",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun GameItem(game: GameUiModel, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Game thumbnail
            Card(
                shape = RoundedCornerShape(4.dp),
            ) {
                CoilImage(
                    data = game.thumbnail,
                    cacheKey = game.thumbnail,
                    modifier = Modifier.height(60.dp).width(60.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Game details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Game title
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Game attributes
                Row(
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "Players: ${game.numberOfPlayers}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = "Time: ${game.playingTime}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = "Rating: ${game.rating}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    shelfName: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }

        Text(
            text = shelfName,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}
