package com.retro99.base.ui.compose

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GameImage(
    imageUrl: String,
    gameId: String,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .sharedScreenBounds(
                key = "$gameId thumbnail",
                renderInOverlayDuringTransition = false,
            ),
        shape = RoundedCornerShape(8.dp),
    ) {
        CoilImage(
            data = imageUrl,
            cacheKey = imageUrl,
            modifier = Modifier.size(size),
            contentScale = ContentScale.FillBounds
        )
    }
}