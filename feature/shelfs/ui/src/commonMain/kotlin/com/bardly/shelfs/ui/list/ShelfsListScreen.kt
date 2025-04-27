package com.bardly.shelfs.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bardly.games.ui.model.GameUiModel
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.base.ui.compose.CoilImage
import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.stringResource
import resources.translations.home_recent_games
import resources.translations.shelves_list

@Composable
fun ShelfsListScreen(
    component: ShelfsListComponent,
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
    viewState: ShelfsListViewState,
    intentDispatcher: IntentDispatcher<ShelfsListIntent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(StringRes.shelves_list),
                style = MaterialTheme.typography.headlineLarge,
            )
        }

        if (viewState.shelfs.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No shelves available",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = viewState.shelfs,
                    key = { it.id }
                ) { shelf ->
                    ShelfItem(
                        shelf = shelf,
                        onShelfClick = { intentDispatcher(ShelfsListIntent.ShelfClicked(shelf)) },
                        onGameClick = { /* Handle game click if needed */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShelfItem(
    shelf: ShelfUiModel,
    onShelfClick: () -> Unit,
    onGameClick: (GameUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onShelfClick)
                .padding(16.dp)
        ) {
            Text(
                text = shelf.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (shelf.games.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = shelf.games,
                        key = { it.id }
                    ) { game ->
                        GamePreviewItem(
                            game = game,
                            onClick = { onGameClick(game) }
                        )
                    }
                }
            } else {
                Text(
                    text = "No games in this shelf",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun GamePreviewItem(
    game: GameUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(120.dp)
            .clickable(onClick = onClick)
    ) {
        // Game thumbnail placeholder
        Box(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (game.thumbnail.isNotEmpty()) {
                CoilImage(game.thumbnail, game.thumbnail)
            } else {
                Text(
                    text = "No Image",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = game.title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
