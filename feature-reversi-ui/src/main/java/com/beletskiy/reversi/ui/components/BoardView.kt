package com.beletskiy.reversi.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.beletskiy.reversi.data.Cell
import com.beletskiy.reversi.data.Disc
import com.beletskiy.reversi.data.MoveOption
import com.beletskiy.reversi.data.PlayerDisc
import com.beletskiy.shared.theme.Accent
import com.beletskiy.shared.theme.GamesTheme

private const val TILES = 8
private const val DIVIDER_WIDTH = 3

@Composable
fun BoardView(
    board: List<List<Cell>>,
    isGameOver: Boolean,
    currentDisc: PlayerDisc,
    modifier: Modifier = Modifier,
    onTileClick: (Int, Int) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
    )
    {
        val dimension = min(this.maxWidth, this.maxHeight)
        val tileSize = dimension / TILES
        val density = LocalDensity.current
        val tileSizePx = with(density) { tileSize.toPx() }

        Canvas(
            modifier = Modifier
                .size(dimension)
                .pointerInput(true) {
                    detectTapGestures { clickOffset ->
                        if (isGameOver) return@detectTapGestures
                        val (row, column) = detectClickedCell(tileSizePx, clickOffset)
                        onTileClick(row, column)
                    }
                },
        ) {
            val sizePx = kotlin.math.min(size.width, size.height)
            val tileSizePx = sizePx / TILES

            //region Draw dividers
            for (i in 1 until TILES) {
                // vertical dividers
                drawLine(
                    color = Accent,
                    start = Offset(i * tileSizePx, 0f),
                    end = Offset(i * tileSizePx, sizePx),
                    strokeWidth = DIVIDER_WIDTH.dp.toPx(),
                    cap = StrokeCap.Round
                )
                // horizontal dividers
                drawLine(
                    color = Accent,
                    start = Offset(0f, i * tileSizePx),
                    end = Offset(sizePx, i * tileSizePx),
                    strokeWidth = DIVIDER_WIDTH.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            //endregion

            board.forEachIndexed { row, rowData ->
                rowData.forEachIndexed { col, cell ->
                    val offsetX = col * tileSizePx
                    val offsetY = row * tileSizePx

                    when (cell.disc) {
                        Disc.NONE -> {
                            if (cell.moveOption == MoveOption.POSSIBLE) {
                                drawCircle(
                                    color = currentDisc.toColor().copy(alpha = 0.3f),
                                    radius = tileSizePx * 0.8f / 2f,
                                    center = Offset(
                                        offsetX + tileSizePx / 2f,
                                        offsetY + tileSizePx / 2f
                                    )
                                )
                            }
                        }

                        Disc.BLACK -> {
                            drawCircle(
                                color = cell.disc.toColor(),
                                radius = tileSizePx * 0.8f / 2f,
                                center = Offset(
                                    offsetX + tileSizePx / 2f,
                                    offsetY + tileSizePx / 2f
                                )
                            )
                        }

                        Disc.WHITE -> {
                            drawCircle(
                                color = cell.disc.toColor(),
                                radius = tileSizePx * 0.8f / 2f,
                                center = Offset(
                                    offsetX + tileSizePx / 2f,
                                    offsetY + tileSizePx / 2f
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFAAAAAA)
@Composable
private fun BoardViewPreview(modifier: Modifier = Modifier) {
    GamesTheme {
        BoardView(
            board = listOf(
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(
                    Cell(),
                    Cell(),
                    Cell(),
                    Cell(disc = Disc.BLACK),
                    Cell(),
                    Cell(),
                    Cell(),
                    Cell()
                ),
                listOf(
                    Cell(),
                    Cell(),
                    Cell(),
                    Cell(),
                    Cell(disc = Disc.WHITE),
                    Cell(),
                    Cell(),
                    Cell()
                ),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
            ),
            isGameOver = false,
            currentDisc = PlayerDisc.BLACK,
            modifier = modifier,
        ) { _, _ ->
        }
    }
}

private fun detectClickedCell(tileSizePx: Float, offset: Offset): Pair<Int, Int> {
    val row = (offset.y / tileSizePx).toInt()
    val column = (offset.x / tileSizePx).toInt()
    return row to column
}
