package com.retro99.base.ui.decompose

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
actual fun SharedTransitionScope.ScreenAnimationProvider(
    animateVisibilityScope: AnimatedVisibilityScope,
    content: @Composable SharedTransitionScope.() -> Unit
) {
    content()
}