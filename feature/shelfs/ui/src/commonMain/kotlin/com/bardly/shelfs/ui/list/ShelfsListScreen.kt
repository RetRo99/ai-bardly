package com.bardly.shelfs.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bardly.games.ui.model.GameUiModel
import com.bardly.shelfs.ui.model.ShelfUiModel
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.base.ui.compose.CoilImage
import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import resources.translations.shelves_cancel
import resources.translations.shelves_create
import resources.translations.shelves_create_new_shelf
import resources.translations.shelves_create_shelf
import resources.translations.shelves_description_optional
import resources.translations.shelves_list
import resources.translations.shelves_no_games
import resources.translations.shelves_no_image
import resources.translations.shelves_no_shelves_available
import resources.translations.shelves_shelf_name

@Composable
fun ShelfsListScreen(
    component: ShelfsListPresenter,
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
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { intentDispatcher(ShelfsListIntent.ShowCreateShelfDialog) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(StringRes.shelves_create_shelf)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                        text = stringResource(StringRes.shelves_no_shelves_available),
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

        if (viewState.showCreateShelfDialog) {
            CreateShelfDialog(
                isCreating = viewState.isCreatingShelf,
                error = viewState.createShelfError,
                onDismiss = { intentDispatcher(ShelfsListIntent.HideCreateShelfDialog) },
                onCreateShelf = { name, description ->
                    intentDispatcher(ShelfsListIntent.CreateShelf(name, description))
                }
            )
        }
    }
}

@Composable
private fun CreateShelfDialog(
    isCreating: Boolean,
    error: StringResource? = null,
    onDismiss: () -> Unit,
    onCreateShelf: (String, String?) -> Unit
) {
    var shelfName by remember { mutableStateOf("") }
    var shelfDescription by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(StringRes.shelves_create_new_shelf)) },
        text = {
            Column {
                OutlinedTextField(
                    value = shelfName,
                    onValueChange = { shelfName = it },
                    label = { Text(stringResource(StringRes.shelves_shelf_name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = shelfDescription,
                    onValueChange = { shelfDescription = it },
                    label = { Text(stringResource(StringRes.shelves_description_optional)) },
                    modifier = Modifier.fillMaxWidth()
                )

                if (isCreating) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                if (error != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(error),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { 
                    onCreateShelf(
                        shelfName,
                        shelfDescription.ifBlank { null }
                    ) 
                },
                enabled = shelfName.isNotBlank() && !isCreating
            ) {
                Text(stringResource(StringRes.shelves_create))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isCreating
            ) {
                Text(stringResource(StringRes.shelves_cancel))
            }
        }
    )
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
                    text = stringResource(StringRes.shelves_no_games),
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
                    text = stringResource(StringRes.shelves_no_image),
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
