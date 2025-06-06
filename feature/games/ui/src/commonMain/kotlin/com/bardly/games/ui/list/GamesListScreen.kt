package com.bardly.games.ui.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.bardly.games.ui.components.GamesLazyColumn
import com.bardly.games.ui.list.GamesListIntent.OpenChatClicked
import com.bardly.games.ui.model.GameUiModel
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.base.ui.compose.keyboardAsState
import com.retro99.paging.ui.collectAsLazyPagingItems
import com.retro99.translations.StringRes
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource
import resources.translations.games_list_search_games

@Composable
fun GamesListScreen(
    component: GamesListComponent,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        GamesScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun GamesScreenContent(
    viewState: GamesListViewState,
    intentDispatcher: IntentDispatcher<GamesListIntent>,
) {
    GamesList(
        games = viewState.games,
        intentDispatcher = intentDispatcher,
        isSearchActive = viewState.isSearchActive,
        query = viewState.query,
        searchResults = viewState.searchResults
    )
}

@Composable
private fun GamesList(
    games: Flow<PagingData<GameUiModel>>,
    isSearchActive: Boolean,
    query: String,
    searchResults: Flow<PagingData<GameUiModel>>,
    intentDispatcher: IntentDispatcher<GamesListIntent>
) {
    Box {
        val focusManager = LocalFocusManager.current

        AnimatedContent(
            isSearchActive,
        ) { isSearchActive ->
            if (isSearchActive) {
                SearchScreenState(
                    query,
                    intentDispatcher,
                    searchResults,
                    focusManager,
                )
            } else {
                LaunchedEffect(Unit) {
                    focusManager.clearFocus()
                }
                val games = games.collectAsLazyPagingItems()
                val gamesState = rememberLazyListState()
                GamesLazyColumn(
                    state = gamesState,
                    lazyItems = games,
                    onGameClicked = { game ->
                        focusManager.clearFocus()
                        intentDispatcher(GamesListIntent.GameClicked(game))
                    },
                    onOpenChatClicked = { title, id ->
                        intentDispatcher(OpenChatClicked(title, id))
                    },
                )
            }
        }
        val isKeyboardOpen by keyboardAsState()
        val offset by animateDpAsState(
            targetValue = if (!isKeyboardOpen) 0.dp else 68.dp,
            animationSpec = tween(durationMillis = 300)
        )
        FloatingActionButton(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.imePadding().align(Alignment.BottomEnd).padding(16.dp).offset {
                IntOffset(
                    0,
                    offset.roundToPx()
                )
            },
            onClick = {
                intentDispatcher(GamesListIntent.SearchStateChanged(!isSearchActive))
            }
        ) {
            Icon(
                imageVector = if (isSearchActive) Icons.Default.Close else Icons.Default.Search,
                contentDescription = null
            )
        }
    }
}

@Composable
fun SearchScreenState(
    query: String,
    intentDispatcher: IntentDispatcher<GamesListIntent>,
    results: Flow<PagingData<GameUiModel>>,
    focusManager: FocusManager,
) {
    val focusRequester = remember { FocusRequester() }
    val searchResults = results.collectAsLazyPagingItems()
    val searchState = rememberLazyListState()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column {
        TextField(
            value = query,
            onValueChange = {
                intentDispatcher(GamesListIntent.SearchQueryChanged(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            placeholder = { Text(stringResource(StringRes.games_list_search_games)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            shape = RoundedCornerShape(28.dp)
        )

        GamesLazyColumn(
            state = searchState,
            lazyItems = searchResults,
            onGameClicked = { game ->
                focusManager.clearFocus()
                intentDispatcher(GamesListIntent.GameClicked(game))
            },
            onOpenChatClicked = { title, id ->
                intentDispatcher(OpenChatClicked(title, id))
            }
        )
    }
}