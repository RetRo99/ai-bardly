package com.retro99.snackbar.api

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Host composable for displaying snackbars. This composable should be placed at the root of your
 * composable hierarchy to ensure snackbars are displayed above all other content.
 *
 * @param message The message to display in the snackbar. If null, the snackbar will be hidden.
 * @param hideSnackBar Callback to hide the snackbar. This should be called when the snackbar is
 *                    dismissed by the user or when the message is no longer valid.
 * @param snackbarPosition The position of the snackbar on the screen. Defaults to [Alignment.TopCenter].
 * @param modifier The modifier to apply to the snackbar host.
 * @param content The content of the screen. This content will be displayed behind the snackbar.
 */
@Composable
fun SnackbarHost(
    message: SnackbarData?,
    hideSnackBar: (SnackbarData?) -> Unit,
    snackbarPosition: Alignment = Alignment.TopCenter,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()

        // Track current and previous message for proper animations
        var currentMessage by remember { mutableStateOf<SnackbarData?>(null) }
        var isVisible by remember { mutableStateOf(false) }

        // Update visibility and current message when message changes
        LaunchedEffect(message) {
            if (message != null) {
                currentMessage = message
                isVisible = true

                // Auto-dismiss logic
                if (message.durationMillis != null) {
                    delay(message.durationMillis.toLong())
                    hideSnackBar(message)
                }
            } else {
                // Start exit animation
                isVisible = false
                // After animation completes, currentMessage will remain for exit animation
                delay(ANIMATION_DURATION_MILLIS.toLong()) // Wait for animation to complete
                currentMessage = null
            }
        }

        AnimatedVisibility(
            visible = isVisible,
            modifier = Modifier.align(snackbarPosition),
            enter = slideInVertically(
                animationSpec = tween(ANIMATION_DURATION_MILLIS)
            ) {
                if (snackbarPosition == Alignment.TopCenter) -it else it
            } + fadeIn(animationSpec = tween(ANIMATION_DURATION_MILLIS)),
            exit = slideOutVertically(
                animationSpec = tween(ANIMATION_DURATION_MILLIS)
            ) {
                if (snackbarPosition == Alignment.TopCenter) -it else it
            } + fadeOut(animationSpec = tween(ANIMATION_DURATION_MILLIS))
        ) {
            currentMessage?.let { value ->
                val actionModifier = if (value.action != null) {
                    Modifier
                        .clickable(
                            role = Role.Button,
                            onClickLabel = "Snackbar Action"
                        ) {
                            value.action.invoke()
                            hideSnackBar(value)
                        }
                } else {
                    Modifier
                }

                Snackbar(
                    modifier = actionModifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        .semantics {
                            contentDescription = "Notification: ${value.title}"
                        },
                    value
                )
            }
        }
    }
}

@Composable
private fun Snackbar(modifier: Modifier, data: SnackbarData) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 10.dp)
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.onErrorContainer, MaterialTheme.shapes.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            Modifier
                .weight(1f, fill = false)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = data.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onError,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

private const val ANIMATION_DURATION_MILLIS = 500
