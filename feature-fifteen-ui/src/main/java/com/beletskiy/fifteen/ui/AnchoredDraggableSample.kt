package com.beletskiy.fifteen.ui

import android.util.Log
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AnchoredDraggableSample() {
    val width = 96.dp
    val squareSize = 48.dp
    val density = LocalDensity.current

    val decay = rememberSplineBasedDecay<Float>()
    val anchorsInitialized = remember { mutableStateOf(false) }

    val dragState = remember {
        AnchoredDraggableState(
            initialValue = 0,
            positionalThreshold = { distance: Float -> distance * 0.3f },
            velocityThreshold = { with(density) { 125.dp.toPx() } },
            snapAnimationSpec = spring(),
            decayAnimationSpec = decay,
        )
    }

    val sizePx = with(density) { squareSize.toPx() }
    Log.d("vitDebug", "sizePx = $sizePx")
    LaunchedEffect(Unit) {
        dragState.updateAnchors(
            DraggableAnchors {
                0 at 0f
                1 at sizePx
            }
        )
        anchorsInitialized.value = true
    }

    Box(
        modifier = Modifier
            .width(width)
            .anchoredDraggable(
                state = dragState,
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        if (anchorsInitialized.value) {
            Log.d("vitDebug", "${IntOffset(dragState.requireOffset().roundToInt(), 0)}")
            Box(
                Modifier
                    .offset { IntOffset(dragState.requireOffset().roundToInt(), 0) }
                    .size(squareSize)
                    .background(Color.DarkGray)
            )
        }
    }
}
