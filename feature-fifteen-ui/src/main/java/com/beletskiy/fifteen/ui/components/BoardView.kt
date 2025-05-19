package com.beletskiy.fifteen.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import kotlin.math.roundToInt

private const val TILES = 4
private const val GAPS = TILES - 1

@Suppress("detekt:LongMethod")
@Composable
fun BoardView(
    board: List<List<Int>>,
    isGameOver: Boolean,
    modifier: Modifier = Modifier,
    onTileClick: (Int, Int) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
    ) {
        val dimension = min(this.maxWidth, this.maxHeight)
        val spacing = 4.dp
        val tileSize = (dimension - spacing * GAPS) / TILES
        val density = LocalDensity.current
        val tileSizePx = with(density) { tileSize.toPx() }
        val spacingPx = with(density) { spacing.toPx() }

        // Build a flat map of tile -> position
        val tilePositions = remember(board) {
            mutableMapOf<Int, Pair<Int, Int>>().apply {
                board.forEachIndexed { row, rowData ->
                    rowData.forEachIndexed { col, tile ->
                        if (tile != 0) put(tile, row to col)
                    }
                }
            }
        }

        // The point is that it's created only once when we receive the first board
        val tileOffsets = remember {
            mutableMapOf<Int, Pair<Animatable<Float, *>, Animatable<Float, *>>>()
        }

        // This Box represents the entire board
        Box(
            modifier = Modifier
                .size(tileSize * TILES + spacing * GAPS)
                .background(Color.LightGray),
        ) {
            tilePositions.forEach { (tile, position) ->
                val (row, col) = position

                // Animatables for every tile => .put() will be called only once,
                // when the tile is first created
                val (animX, animY) = tileOffsets.getOrPut(tile) {
                    val startX = col * (tileSizePx + spacingPx)
                    val startY = row * (tileSizePx + spacingPx)
                    Animatable(startX) to Animatable(startY)
                }

                // New target position is calculated based on the current row and column
                val targetX = col * (tileSizePx + spacingPx)
                val targetY = row * (tileSizePx + spacingPx)

                // Animate to new target (visible if animX, animY are different from
                // the current targetX, targetY)
                LaunchedEffect(targetX, targetY) {
                    animX.animateTo(targetX)
                    animY.animateTo(targetY)
                }

                // This Box represents a single tile
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(animX.value.roundToInt(), animY.value.roundToInt())
                        }
                        .size(tileSize)
                        .clickable(
                            // Ripple removed: layout-based ripple conflicts with animated offset
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) {
                            if (isGameOver) return@clickable
                            onTileClick(row, col)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val drawable = numberToDrawable(tile)
                    Image(
                        painterResource(drawable),
                        contentDescription = tile.toString(),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@Suppress("detekt:MagicNumber")
@Preview
@Composable
fun BoardViewPreview(modifier: Modifier = Modifier) {
    BoardView(
        board = listOf(
            listOf(1, 2, 3, 4),
            listOf(5, 6, 7, 8),
            listOf(9, 10, 11, 12),
            listOf(13, 14, 15, 0)
        ),
        isGameOver = false,
        modifier = modifier,
    ) { _, _ ->
    }
}
