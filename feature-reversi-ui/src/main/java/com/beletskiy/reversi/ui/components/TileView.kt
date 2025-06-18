package com.beletskiy.reversi.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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

@Composable
fun TileView(
    fromCell: Disc,
    toCell: Disc,
    playerDisc: PlayerDisc,
    isPossibleMove: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
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

        if (toCell == Disc.NONE) {
            if (isPossibleMove) {
                drawPossibleDisc(
                    tileSizePx = tileSizePx,
                    color = playerDisc.toColor(),
                )
            }
        } else {
//            if (fromCell.disc == toCell.disc) {
                drawDisc(
                    tileSizePx = tileSizePx,
                    color = toCell.toColor(),
                )
//            } else {
//                animateDisc(
//                    tileSizePx = tileSizePx,
//                    fromDisc = fromCell.disc,
//                    toDisc = toCell.disc,
//                    flipProgress = 1f, // TODO: replace with actual animation progress
//                )
//            }
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

private fun DrawScope.animateDisc(
    tileSizePx: Float,
    fromDisc: Disc,
    toDisc: Disc,
    flipProgress: Float,
) {
    val center = Offset(tileSizePx / 2f, tileSizePx / 2f)
    val radius = tileSizePx * 0.8f / 2f
    val scaleX = 1f - abs(flipProgress - 0.5f) * 2
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
