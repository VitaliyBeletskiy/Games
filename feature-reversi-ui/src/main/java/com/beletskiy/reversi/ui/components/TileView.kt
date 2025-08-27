package com.beletskiy.reversi.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beletskiy.reversi.data.Disc
import com.beletskiy.reversi.data.PlayerDisc
import kotlin.math.abs

private const val ANIM_DURATION = 1_000

@Composable
fun TileView(
    fromDisc: Disc,
    toDisc: Disc,
    playerDisc: PlayerDisc,
    isPossibleMove: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val progress = remember { Animatable(1f) }

    LaunchedEffect(fromDisc, toDisc) {
        if (fromDisc == toDisc) {
            progress.snapTo(1f)
        } else {
            progress.snapTo(0f)
            progress.animateTo(1f, tween(durationMillis = ANIM_DURATION))
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectTapGestures { _ ->
                    onClick()
                }
            },
    ) {
        val tileSizePx = size.minDimension

        when {
            toDisc == Disc.NONE && isPossibleMove -> {
                drawPossibleDisc(
                    tileSizePx = tileSizePx,
                    color = playerDisc.toColor(),
                )
            }
            fromDisc == Disc.NONE && toDisc != Disc.NONE -> {
                drawNewDisc(
                    tileSizePx= tileSizePx,
                    toDisc = toDisc,
                    flipProgress = progress.value,
                )
            }
            fromDisc != toDisc && toDisc != Disc.NONE -> {
                flipDisc(
                    tileSizePx = tileSizePx,
                    fromDisc = fromDisc,
                    toDisc = toDisc,
                    flipProgress = progress.value,
                )
            }
            fromDisc == toDisc -> {
                drawDisc(
                    tileSizePx = tileSizePx,
                    color = toDisc.toColor(),
                )
            }
        }
    }
}

private fun DrawScope.drawPossibleDisc(
    tileSizePx: Float,
    color: Color,
    strokeWidthDp: Dp = 3.dp,
) {
    val strokeWidth = strokeWidthDp.toPx()
    val radius = tileSizePx * 0.7f / 2f
    val topLeft = Offset(
        tileSizePx / 2 - radius,
        tileSizePx / 2 - radius,
    )

    drawArc(
        color = color.copy(alpha = 0.6f),
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        topLeft = topLeft,
        size = Size(radius * 2, radius * 2),
        style = Stroke(
            width = strokeWidth,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
        )
    )
}

private fun DrawScope.drawDisc(
    tileSizePx: Float,
    color: Color,
) {
    drawCircle(
        color = color,
        radius = tileSizePx * 0.8f / 2f,
        center = Offset(
            tileSizePx / 2f,
            tileSizePx / 2f
        )
    )
}

private fun DrawScope.flipDisc(
    tileSizePx: Float,
    fromDisc: Disc,
    toDisc: Disc,
    flipProgress: Float,
) {
    val center = Offset(tileSizePx / 2f, tileSizePx / 2f)
    val radius = tileSizePx * 0.8f / 2f
    val scaleX = abs(2* flipProgress - 1)
    val currentDisc = if (flipProgress < 0.5f) fromDisc else toDisc

    withTransform({
        scale(scaleX = scaleX, scaleY = 1f, pivot = center)
    }) {
        drawCircle(
            color = currentDisc.toColor(),
            radius = radius,
            center = center
        )
    }
}

private fun DrawScope.drawNewDisc(
    tileSizePx: Float,
    toDisc: Disc,
    flipProgress: Float,
) {
    val center = Offset(tileSizePx / 2f, tileSizePx / 2f)
    val radius = tileSizePx * 0.8f / 2f

    withTransform({
        scale(scaleX = flipProgress, scaleY = flipProgress, pivot = center)
    }) {
        drawCircle(
            color = toDisc.toColor(),
            radius = radius,
            center = center
        )
    }
}
