package com.bardly.shelfs.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.stringResource
import resources.translations.home_recent_games

@Composable
fun ShelfsListScreen(
    component: ShelfsListComponent,
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
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Shelfs list",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
    }
}
