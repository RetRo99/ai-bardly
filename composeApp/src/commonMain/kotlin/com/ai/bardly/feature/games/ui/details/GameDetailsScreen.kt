package com.ai.bardly.feature.games.ui.details

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.amount_of_players
import ai_bardly.composeapp.generated.resources.game_age_recommendation
import ai_bardly.composeapp.generated.resources.game_complexity
import ai_bardly.composeapp.generated.resources.game_length
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.ui.CoilImage
import com.ai.bardly.util.LocalScreenAnimationScope
import com.ai.bardly.util.LocalScreenTransitionScope
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameDetailsScreen(
    game: GameUiModel,
) {
    BaseScreen<GameDetailsViewModel, GameDetailsViewState, GameDetailsIntent>(
        parameters = arrayOf(game)
    ) { viewState, intentDispatcher ->
        GamesScreenContent(
            game = viewState.game,
            intentDispatcher = intentDispatcher,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun GamesScreenContent(
    game: GameUiModel,
    intentDispatcher: IntentDispatcher<GameDetailsIntent>,
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
            IconButton(onClick = { intentDispatcher(GameDetailsIntent.NavigateBack) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }

        // Game Image
        with(LocalScreenTransitionScope.current) {
            Card(
                modifier = Modifier.wrapContentSize().sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = "${game.id} thumbnail",
                    ), animatedVisibilityScope = LocalScreenAnimationScope.current
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
                            animatedVisibilityScope = LocalScreenAnimationScope.current

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
                                animatedVisibilityScope = LocalScreenAnimationScope.current
                            ),
                            text = "\uD83D\uDCC5 ${game.yearPublished}",
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = "|",
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier.sharedBounds(
                                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                                sharedContentState = rememberSharedContentState(
                                    key = "${game.id} rating",
                                ),
                                animatedVisibilityScope = LocalScreenAnimationScope.current
                            ),
                            text = "⭐ ${game.rating}",
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
            )

            Spacer(modifier = Modifier.height(16.dp))

            Description(game.description)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun GameInformationCards(
    game: GameUiModel,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        with(LocalScreenTransitionScope.current) {
            GameInfoCard(
                modifier = Modifier.sharedBounds(
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                    sharedContentState = rememberSharedContentState(
                        key = "${game.id} numberOfPlayers",
                    ),
                    animatedVisibilityScope = LocalScreenAnimationScope.current
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
                    animatedVisibilityScope = LocalScreenAnimationScope.current
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
                    animatedVisibilityScope = LocalScreenAnimationScope.current
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
                    animatedVisibilityScope = LocalScreenAnimationScope.current
                ),
                label = Res.string.game_complexity,
                value = "${game.complexity}/5"
            )
        }
    }
}

@Composable
private fun Description(description: String) {
    val richTextState = rememberRichTextState()
    richTextState.setMarkdown(description)

    RichText(
        state = richTextState,
        fontSize = 14.sp
    )
}

@Composable
fun GameInfoCard(
    label: StringResource,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
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
                textAlign = TextAlign.Center
            )
        }
    }
}
