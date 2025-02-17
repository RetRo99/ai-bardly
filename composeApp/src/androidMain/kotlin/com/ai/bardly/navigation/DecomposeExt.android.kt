package com.ai.bardly.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.ai.bardly.util.LocalScreenAnimationScope
import com.ai.bardly.util.LocalScreenTransitionScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
actual fun SharedTransitionScope.ScreenAnimationProvider(
    animateVisibilityScope: AnimatedVisibilityScope,
    content: @Composable SharedTransitionScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalScreenTransitionScope provides this@ScreenAnimationProvider,
        LocalScreenAnimationScope provides animateVisibilityScope,
    ) {
        content()
    }
}