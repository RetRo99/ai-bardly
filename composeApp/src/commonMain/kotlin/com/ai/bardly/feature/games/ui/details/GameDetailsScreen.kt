package com.ai.bardly.feature.games.ui.details

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.amount_of_players
import ai_bardly.composeapp.generated.resources.game_age_recommendation
import ai_bardly.composeapp.generated.resources.game_complexity
import ai_bardly.composeapp.generated.resources.game_length
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ai.bardly.GameUiModel
import com.ai.bardly.ui.BaseScreen
import com.ai.bardly.ui.CoilImage
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.GameDetailsScreen(
    game: GameUiModel,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    BaseScreen<GameDetailsViewModel, GameDetailsViewState>(
        parameters = arrayOf(game)
    ) { viewModel, viewState ->
        GamesScreenContent(
            game = viewState.game,
            onBackClick = viewModel::onBackClick,
            animatedVisibilityScope = animatedVisibilityScope,
        )

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.GamesScreenContent(
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
            modifier = Modifier.wrapContentSize().sharedBounds(
                sharedContentState = rememberSharedContentState(
                    key = "${game.id} thumbnail",
                ), animatedVisibilityScope
            ).align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            CoilImage(
                data = game.thumbnail,
                cacheKey = game.thumbnail,
                modifier = Modifier
                    .wrapContentSize()
                    .height(180.dp),
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
                    modifier = Modifier.sharedBounds(
                        resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                        sharedContentState = rememberSharedContentState(
                            key = "${game.id} title",
                        ),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                    text = game.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Row {
                    Text(
                        modifier = Modifier.sharedBounds(
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                            sharedContentState = rememberSharedContentState(
                                key = "${game.id} year",
                            ),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                        text = "\uD83D\uDCC5 ${game.yearPublished}",
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
                        modifier = Modifier.sharedBounds(
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                            sharedContentState = rememberSharedContentState(
                                key = "${game.id} rating",
                            ),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                        text = "⭐ ${game.rating}",
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
            modifier = Modifier.sharedBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                sharedContentState = rememberSharedContentState(
                    key = "${game.id} numberOfPlayers",
                ),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            label = Res.string.amount_of_players,
            value = game.numberOfPlayers
        )
        GameInfoCard(
            modifier = Modifier.sharedBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                sharedContentState = rememberSharedContentState(
                    key = "${game.id} playingTime",
                ),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            label = Res.string.game_length,
            value = game.playingTime
        )
        GameInfoCard(
            modifier = Modifier.sharedBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                sharedContentState = rememberSharedContentState(
                    key = "${game.id} ageRange",
                ),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            label = Res.string.game_age_recommendation,
            value = game.ageRange
        )
        GameInfoCard(
            modifier = Modifier.sharedBounds(
                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                sharedContentState = rememberSharedContentState(
                    key = "${game.id} complexity",
                ),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            label = Res.string.game_complexity,
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
