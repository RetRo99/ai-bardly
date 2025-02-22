package com.retro99.base.ui.compose

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.SharedTransitionScope.ResizeMode.Companion.ScaleToBounds
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.retro99.translations.PluralRes
import com.retro99.translations.StringRes
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import resources.translations.days_ago
import resources.translations.hours_ago
import resources.translations.just_now
import resources.translations.minutes_ago
import resources.translations.weeks_ago

@Composable
fun Int.toDp(): Dp {
    val value = this
    return with(LocalDensity.current) { value.toDp() }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 600
    return rememberUpdatedState(isImeVisible)
}

val LocalScreenAnimationScope = compositionLocalOf<AnimatedVisibilityScope?> {
    null
}

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalScreenTransitionScope = compositionLocalOf<SharedTransitionScope?> {
    null
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.sharedScreenBounds(
    key: Any,
    renderInOverlayDuringTransition: Boolean = false,
    resizeMode: SharedTransitionScope.ResizeMode = ScaleToBounds(ContentScale.FillWidth, Center)
): Modifier = composed {
    val transitionScope = LocalScreenTransitionScope.current
    val animationScope = LocalScreenAnimationScope.current

    if (transitionScope == null || animationScope == null) {
        return@composed this
    }

    with(transitionScope) {
        val sharedContentState = rememberSharedContentState(
            key = key,
        )
        this@composed.sharedBounds(
            sharedContentState = sharedContentState,
            renderInOverlayDuringTransition = renderInOverlayDuringTransition,
            animatedVisibilityScope = animationScope,
            resizeMode = resizeMode,
        )
    }
}

@Composable
fun LocalDateTime.timeAgo(): String {
    val now = Clock.System.now()
    val duration = now - this.toInstant(TimeZone.currentSystemDefault())


    return when {
        duration.inWholeDays > 7 -> pluralStringResource(
            PluralRes.weeks_ago,
            (duration.inWholeDays / 7).toInt(), duration.inWholeDays / 7
        )

        duration.inWholeDays > 0 -> pluralStringResource(
            PluralRes.days_ago,
            duration.inWholeDays.toInt(), duration.inWholeDays
        )

        duration.inWholeHours > 0 -> pluralStringResource(
            PluralRes.hours_ago,
            duration.inWholeHours.toInt(), duration.inWholeHours
        )

        duration.inWholeMinutes > 0 -> pluralStringResource(
            PluralRes.minutes_ago,
            duration.inWholeMinutes.toInt(), duration.inWholeMinutes
        )

        else -> stringResource(StringRes.just_now)
    }

}
