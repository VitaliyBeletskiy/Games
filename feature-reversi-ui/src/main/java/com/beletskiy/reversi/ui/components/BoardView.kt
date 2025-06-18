package com.beletskiy.reversi.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.beletskiy.reversi.data.Disc
import com.beletskiy.reversi.data.PlayerDisc
import com.beletskiy.shared.theme.Accent
import com.beletskiy.shared.theme.GamesTheme

private const val BOARD_SIZE = 8
private const val DIVIDER_WIDTH = 3

@Composable
fun BoardView(
    board: List<List<Disc>>,
    possibleMoves: Set<Pair<Int, Int>>,
    currentDisc: PlayerDisc,
    modifier: Modifier = Modifier,
    onTileClick: (Int, Int) -> Unit,
) {
    val previousBoard = remember { mutableStateMapOf<Pair<Int, Int>, Disc>() }

    if (previousBoard.isEmpty()) {
        for (row in board.indices) {
            for (col in board[row].indices) {
                previousBoard[row to col] = board[row][col]
            }
        }
    }
    LaunchedEffect(board) {
        previousBoard.clear()
        board.forEachIndexed { row, rowData ->
            rowData.forEachIndexed { col, cell ->
                previousBoard[row to col] = cell
            }
        }
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
    ) {
        val boardSizeDp = min(this.maxWidth, this.maxHeight)

        Canvas(
            modifier = Modifier
                .size(boardSizeDp),
        ) {
            drawBoardGrid(
                boardSize = BOARD_SIZE,
                gridThickness = DIVIDER_WIDTH,
                color = Accent,
            )
        }

        Column {
            repeat(BOARD_SIZE) { row ->
                Row {
                    repeat(BOARD_SIZE) { col ->
                        TileView(
                            fromCell = previousBoard[row to col] ?: Disc.NONE,
                            toCell = board[row][col],
                            playerDisc = currentDisc,
                            isPossibleMove = possibleMoves.contains(row to col),
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f),
                        ) {
                            onTileClick(row, col)
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawBoardGrid(
    boardSize: Int,
    gridThickness: Int,
    color: Color,
) {
    val sizePx = kotlin.math.min(size.width, size.height)
    val tileSizePx = sizePx / boardSize

    for (i in 1 until boardSize) {
        // vertical dividers
        drawLine(
            color = color,
            start = Offset(i * tileSizePx, 0f),
            end = Offset(i * tileSizePx, sizePx),
            strokeWidth = gridThickness.dp.toPx(),
            cap = StrokeCap.Round
        )
        // horizontal dividers
        drawLine(
            color = color,
            start = Offset(0f, i * tileSizePx),
            end = Offset(sizePx, i * tileSizePx),
            strokeWidth = gridThickness.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFE8D0C1)
@Composable
private fun BoardViewPreview(modifier: Modifier = Modifier) {
    GamesTheme {
        BoardView(
            board = listOf(
                List(BOARD_SIZE) { Disc.NONE },
                List(BOARD_SIZE) { Disc.NONE },
                List(BOARD_SIZE) { Disc.NONE },
                listOf(
                    Disc.NONE,
                    Disc.NONE,
                    Disc.NONE,
                    Disc.BLACK,
                    Disc.NONE,
                    Disc.NONE,
                    Disc.NONE,
                    Disc.NONE,
                ),
                listOf(
                    Disc.NONE,
                    Disc.NONE,
                    Disc.NONE,
                    Disc.NONE,
                    Disc.WHITE,
                    Disc.NONE,
                    Disc.NONE,
                    Disc.NONE,
                ),
                List(BOARD_SIZE) { Disc.NONE },
                List(BOARD_SIZE) { Disc.NONE },
                List(BOARD_SIZE) { Disc.NONE },
            ),
            currentDisc = PlayerDisc.BLACK,
            possibleMoves = setOf(0 to 0),
            modifier = modifier,
        ) { _, _ ->
        }
    }
}
