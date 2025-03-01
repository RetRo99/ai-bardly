package com.retro99.base.ui.decompose

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.retro99.base.ui.compose.LocalScreenAnimationScope
import com.retro99.base.ui.compose.LocalScreenTransitionScope

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