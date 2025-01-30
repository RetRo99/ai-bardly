package com.ai.bardly.feature.games.ui.details

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.amount_of_players
import ai_bardly.composeapp.generated.resources.game_age_recommendation
import ai_bardly.composeapp.generated.resources.game_complexity
import ai_bardly.composeapp.generated.resources.game_length
import ai_bardly.composeapp.generated.resources.ic_chat
import ai_bardly.composeapp.generated.resources.ic_rating
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
import androidx.compose.material3.FloatingActionButton
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
import com.ai.bardly.feature.games.ui.components.GameImage
import com.ai.bardly.feature.games.ui.components.SharedTransitionText
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.util.LocalScreenAnimationScope
import com.ai.bardly.util.LocalScreenTransitionScope
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichText
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
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

@Composable
fun GamesScreenContent(
    game: GameUiModel,
    intentDispatcher: IntentDispatcher<GameDetailsIntent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar(onBackClick = { intentDispatcher(GameDetailsIntent.NavigateBack) })

        GameDetailsContent(
            game = game,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun GameDetailsContent(
    game: GameUiModel,
    intentDispatcher: IntentDispatcher<GameDetailsIntent>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        GameImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentSize(),
            imageUrl = game.thumbnail,
            gameId = game.id,
            size = 180.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        GameHeaderSection(
            rating = game.rating,
            yearPublished = game.yearPublished,
            title = game.title,
            id = game.id,
            onChatClicked = { intentDispatcher(GameDetailsIntent.OpenChatClicked) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        GameDescriptionSection(game = game)
    }
}

@Composable
private fun GameHeaderSection(
    rating: String,
    yearPublished: String,
    title: String,
    id: Int,
    onChatClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        TitleAndRating(
            rating = rating, yearPublished = yearPublished, title = title, id = id,
        )
        Spacer(modifier = Modifier.weight(1f))
        OpenChatButton(onChatClicked)
    }
}

@Composable
private fun OpenChatButton(
    onChatClicked: () -> Unit,
) {
    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        onClick = onChatClicked,
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_chat),
            contentDescription = null,
        )
    }
}

@Composable
private fun TitleAndRating(
    rating: String,
    yearPublished: String,
    title: String,
    id: Int
) {
    Column {
        SharedTransitionText(
            key = "$id title",
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Row {
            SharedTransitionText(
                key = "$id year",
                text = yearPublished,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "|",
                style = MaterialTheme.typography.bodyMedium
            )

            SharedTransitionText(
                key = "$id rating",
                iconRes = Res.drawable.ic_rating,
                text = rating,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun GameDescriptionSection(
    game: GameUiModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        GameInformationCards(game = game)

        Spacer(modifier = Modifier.height(16.dp))

        Description(game.description)
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
