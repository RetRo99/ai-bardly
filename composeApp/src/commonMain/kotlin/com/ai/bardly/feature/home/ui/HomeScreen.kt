package com.ai.bardly.feature.home.ui

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.home_explore_now
import ai_bardly.composeapp.generated.resources.home_greeting_subtitle
import ai_bardly.composeapp.generated.resources.home_greeting_title
import ai_bardly.composeapp.generated.resources.home_new_game_available
import ai_bardly.composeapp.generated.resources.home_recent_games
import ai_bardly.composeapp.generated.resources.home_whats_new
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher
import com.ai.bardly.feature.games.ui.components.GamesLazyGrid
import com.ai.bardly.feature.games.ui.details.GameDetailsScreen
import com.ai.bardly.feature.games.ui.model.GameUiModel
import com.ai.bardly.feature.home.ui.root.RootHomeComponent
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimator
import com.arkivanov.decompose.extensions.compose.stack.animation.isFront
import com.arkivanov.essenty.backhandler.BackHandler
import org.jetbrains.compose.resources.stringResource

@Composable
fun RootHome(
    component: RootHomeComponent,
) {
    ChildStack(
        component.childStack,
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackClicked,
        )
    ) { child ->
        when (val screen = child.instance) {
            is RootHomeComponent.HomeChild.Home -> HomeScreen(screen.component)
            is RootHomeComponent.HomeChild.GameDetails -> GameDetailsScreen(screen.component)
        }
    }
}

@Composable
fun HomeScreen(
    component: HomeComponent,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        HomeScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun HomeScreenContent(
    viewState: HomeViewState,
    intentDispatcher: IntentDispatcher<HomeIntent>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GreetingSection()
        WhatsNewSection()
        RecentGamesSection(
            recentGames = viewState.recentGames,
            onOpenChatClicked = { title, id ->
                intentDispatcher(
                    HomeIntent.OpenChatClicked(title, id)
                )
            },
            onGameClicked = {
                intentDispatcher(
                    HomeIntent.OpenGameDetails(it)
                )
            },
        )
    }
}

@Composable
fun GreetingSection() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(Res.string.home_greeting_title),
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = stringResource(Res.string.home_greeting_subtitle),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun WhatsNewSection() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.home_whats_new),
            style = MaterialTheme.typography.headlineLarge,
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(Res.string.home_new_game_available),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = stringResource(Res.string.home_explore_now),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentGamesSection(
    recentGames: List<GameUiModel>,
    onOpenChatClicked: (String, Int) -> Unit,
    onGameClicked: (GameUiModel) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(Res.string.home_recent_games),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )

        GamesLazyGrid(
            itemCount = { recentGames.size },
            getItem = { index -> recentGames[index] },
            getKey = { index -> recentGames[index].id },
            onGameClicked = onGameClicked,
            onOpenChatClicked = onOpenChatClicked
        )
    }
}

fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T> =
    stackAnimation(
        animator = iosLikeSlide(),
        predictiveBackParams = {
            PredictiveBackParams(
                backHandler = backHandler,
                onBack = onBack,
            )
        },
    )

private fun iosLikeSlide(animationSpec: FiniteAnimationSpec<Float> = tween()): StackAnimator =
    stackAnimator(animationSpec = animationSpec) { factor, direction ->
        Modifier
            .then(if (direction.isFront) Modifier else Modifier.fade(factor + 1F))
            .offsetXFactor(factor = if (direction.isFront) factor else factor * 0.5F)
    }

private fun Modifier.fade(factor: Float) =
    drawWithContent {
        drawContent()
        drawRect(color = Color(red = 0F, green = 0F, blue = 0F, alpha = (1F - factor) / 4F))
    }

private fun Modifier.offsetXFactor(factor: Float): Modifier =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = (placeable.width.toFloat() * factor).toInt(), y = 0)
        }
    }