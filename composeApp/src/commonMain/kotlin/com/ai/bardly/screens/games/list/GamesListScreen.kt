package com.ai.bardly.screens.games.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ai.bardly.ui.GameCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GamesScreen(
) {
    val viewModel = koinViewModel<GamesListViewModel>()
    GamesScreenContent()
}

@Composable
fun GamesScreenContent() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Ensures 2 cards per row
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        val gameList = listOf(
            "Catan", "Risk", "Monopoly", "Chess", "Scrabble", "Uno"
        )

        items(gameList) { game ->
            GameCard(game)
        }
    }
}
