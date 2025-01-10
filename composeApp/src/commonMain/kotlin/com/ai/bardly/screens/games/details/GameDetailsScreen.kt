package com.ai.bardly.screens.games.details

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.age
import ai_bardly.composeapp.generated.resources.complexity
import ai_bardly.composeapp.generated.resources.game_length
import ai_bardly.composeapp.generated.resources.players
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ai.bardly.GameUiModel
import com.ai.bardly.base.BaseViewState
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.GameDetailsScreen(
    game: GameUiModel,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val viewModel: GameDetailsViewModel = koinViewModel { parametersOf(game) }
    val viewState = viewModel.viewState.collectAsState()
    GamesScreenContent(
        state = viewState,
        onBackClick = viewModel::onBackClick,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GamesScreenContent(
    state: State<BaseViewState<GameDetailsViewState>>,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    when (val viewState = state.value) {
        is BaseViewState.Loading -> {
            // Loading state
        }

        is BaseViewState.Error -> {
            // Error state
        }

        is BaseViewState.Loaded -> {
            GameDetails(
                game = viewState.data.game,
                onBackClick = onBackClick,
                animatedVisibilityScope = animatedVisibilityScope,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GameDetails(
    game: GameUiModel,
    onBackClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Bar with Back Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }

        // Game Image
        Card(
            modifier = Modifier.fillMaxWidth().sharedElement(
                state = rememberSharedContentState(
                    key = "${game.listNumber} thumbnail",
                ), animatedVisibilityScope
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(game.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.FillBounds
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Game Title and Meta Information
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    modifier = Modifier.sharedElement(
                        state = rememberSharedContentState(
                            key = "${game.listNumber} title",
                        ), animatedVisibilityScope
                    ).skipToLookaheadSize(),
                    text = game.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Row {
                    Text(
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(
                                key = "${game.listNumber} year",
                            ), animatedVisibilityScope
                        ),
                        text = game.yearPublished,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp), // Add padding for spacing around the separator
                        text = "|",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(
                                key = "${game.listNumber} rating",
                            ), animatedVisibilityScope
                        ),
                        text = game.rating,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description Section
        Text(
            text = "Description",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        GameInformationCards(
            game = game,
            animatedVisibilityScope = animatedVisibilityScope,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Description(game.description)
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GameInformationCards(
    game: GameUiModel,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameInfoCard(
            modifier = Modifier.sharedElement(
                state = rememberSharedContentState(
                    key = "${game.listNumber} numberOfPlayers",
                ), animatedVisibilityScope
            ).skipToLookaheadSize(),
            label = Res.string.players,
            value = game.numberOfPlayers
        )
        GameInfoCard(
            modifier = Modifier.sharedElement(
                state = rememberSharedContentState(
                    key = "${game.listNumber} playingTime",
                ), animatedVisibilityScope
            ).skipToLookaheadSize(),
            label = Res.string.game_length,
            value = game.playingTime
        )
        GameInfoCard(
            modifier = Modifier.sharedElement(
                state = rememberSharedContentState(
                    key = "${game.listNumber} ageRange",
                ), animatedVisibilityScope
            ).skipToLookaheadSize(),
            label = Res.string.age,
            value = game.ageRange
        )
        GameInfoCard(
            modifier = Modifier.sharedElement(
                state = rememberSharedContentState(
                    key = "${game.listNumber} complexity",
                ), animatedVisibilityScope
            ).skipToLookaheadSize(),
            label = Res.string.complexity,
            value = "${game.complexity}/5"
        )
    }
}

@Composable
private fun Description(description: String) {
    Text(
        text = description,
        color = Color.Black,
        fontSize = 14.sp
    )
}

@Composable
fun GameInfoCard(
    label: StringResource,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF0F0F0)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier,
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(label),
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}
