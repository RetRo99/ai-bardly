package com.ai.bardly.feature.games.ui.list

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.games_list_search_games
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
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher
import com.ai.bardly.feature.games.ui.components.GamesLazyColumn
import com.ai.bardly.feature.games.ui.list.GamesListIntent.OpenChatClicked
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.paging.collectAsLazyPagingItems
import com.ai.bardly.util.keyboardAsState
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource

@Composable
fun GamesListScreen(
    component: GamesListComponent
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
                    itemCount = { games.itemCount },
                    getItem = games::get,
                    getKey = { games.peek(it)?.id ?: it },
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
            placeholder = { Text(stringResource(Res.string.games_list_search_games)) },
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
            itemCount = { searchResults.itemCount },
            getItem = searchResults::get,
            getKey = { searchResults.peek(it)?.id ?: it },
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