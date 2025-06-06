package com.bardly.shelfs.ui.details

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.stringResource
import resources.translations.shelf_details_back
import resources.translations.shelf_details_delete
import resources.translations.shelf_details_games_count
import resources.translations.shelf_details_players
import resources.translations.shelf_details_rating
import resources.translations.shelf_details_time
import resources.translations.shelves_no_games
import resources.translations.shelf_details_confirm_delete
import resources.translations.shelf_details_confirm_delete_message
import resources.translations.shelf_details_cancel
import resources.translations.shelf_details_confirm_remove_game
import resources.translations.shelf_details_confirm_remove_game_message
import resources.translations.shelf_details_remove
import resources.translations.shelf_details_remove_game_description

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
            onBackClick = { intentDispatcher(ShelfDetailsIntent.NavigateBack) },
            onDeleteClick = { intentDispatcher(ShelfDetailsIntent.ShowDeleteConfirmationDialog) }
        )

        // Show confirmation dialogs if needed
        if (viewState.isDeleteConfirmationDialogVisible) {
            DeleteConfirmationDialog(
                shelfName = viewState.shelf.name,
                onConfirm = { intentDispatcher(ShelfDetailsIntent.ConfirmDeleteShelf) },
                onDismiss = { intentDispatcher(ShelfDetailsIntent.HideDeleteConfirmationDialog) }
            )
        }

        if (viewState.isRemoveGameConfirmationDialogVisible && viewState.gameToRemove != null) {
            RemoveGameConfirmationDialog(
                gameTitle = viewState.gameToRemove.title,
                onConfirm = { intentDispatcher(ShelfDetailsIntent.ConfirmRemoveGameFromShelf) },
                onDismiss = { intentDispatcher(ShelfDetailsIntent.HideRemoveGameConfirmationDialog) }
            )
        }

        // Games section
        Text(
            text = stringResource(StringRes.shelf_details_games_count, viewState.shelf.games.size),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // List of games
        viewState.shelf.games.forEach { game ->
            GameItem(
                game = game,
                onClick = { intentDispatcher(ShelfDetailsIntent.GameClicked(game)) },
                onRemoveClick = { intentDispatcher(ShelfDetailsIntent.ShowRemoveGameConfirmationDialog(it)) }
            )
        }

        // If no games, show a message
        if (viewState.shelf.games.isEmpty()) {
            Text(
                text = stringResource(StringRes.shelves_no_games),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun GameItem(
    game: GameUiModel, 
    onClick: () -> Unit, 
    modifier: Modifier = Modifier,
    onRemoveClick: (GameUiModel) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Content
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
                            text = stringResource(StringRes.shelf_details_players, game.numberOfPlayers),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Text(
                            text = stringResource(StringRes.shelf_details_time, game.playingTime),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(end = 8.dp)
                        )

                        Text(
                            text = stringResource(StringRes.shelf_details_rating, game.rating),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Close button at top right
            IconButton(
                onClick = { onRemoveClick(game) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(StringRes.shelf_details_remove_game_description)
                )
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    shelfName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(StringRes.shelf_details_confirm_delete)) },
        text = { Text(stringResource(StringRes.shelf_details_confirm_delete_message, shelfName)) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(stringResource(StringRes.shelf_details_delete))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(StringRes.shelf_details_cancel))
            }
        }
    )
}

@Composable
private fun RemoveGameConfirmationDialog(
    gameTitle: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(StringRes.shelf_details_confirm_remove_game)) },
        text = { Text(stringResource(StringRes.shelf_details_confirm_remove_game_message, gameTitle)) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(stringResource(StringRes.shelf_details_remove))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(StringRes.shelf_details_cancel))
            }
        }
    )
}

@Composable
private fun TopBar(
    shelfName: String,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(StringRes.shelf_details_back)
            )
        }

        Text(
            text = shelfName,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(StringRes.shelf_details_delete)
            )
        }
    }
}
