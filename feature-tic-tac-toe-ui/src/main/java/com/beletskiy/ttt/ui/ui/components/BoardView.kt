@file:Suppress("MagicNumber")

package com.beletskiy.ttt.ui.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.beletskiy.ttt.data.Mark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("detekt:CyclomaticComplexMethod", "detekt:LongMethod")
@Composable
fun BoardView(
    board: List<List<Mark?>>,
    isGameOver: Boolean,
    modifier: Modifier = Modifier,
    onCellClicked: (row: Int, column: Int) -> Unit = { _, _ -> },
) {
    var clickedCell: Pair<Int, Int>? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()
    var animations = rememberSaveable { emptyAnimations() }

    /* If the animation is cancelled half-way because of config changes (or other reasons),
     * we just snap it to 1f, that is to its final value. */
    LaunchedEffect(Unit) {
        delay(50) // it can sometimes make the transition less jarring in highly dynamic UIs
        for (i in 0..2) {
            for (j in 0..2) {
                if (animations[i][j].value > 0f && animations[i][j].value < 1f) {
                    animations[i][j].snapTo(1f)
                }
            }
        }
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
    ) {
        val dimension = min(this.maxWidth, this.maxHeight) // to make it square
        Canvas(
            modifier = Modifier
                .size(dimension)
                .pointerInput(true) {
                    detectTapGestures { clickOffset ->
                        if (isGameOver) return@detectTapGestures
                        val (row, column) = detectClickedCell(size, clickOffset)
                        clickedCell = row to column
                        onCellClicked(row, column)
                    }
                },
        ) {
            val sizePx = kotlin.math.min(size.width, size.height)
            val cellSize = sizePx / 3f
            val padding = 0.2f

            //region Draw dividers
            for (i in 1..2) {
                // vertical dividers
                drawLine(
                    color = Color.Black,
                    start = Offset(i * cellSize, 0f),
                    end = Offset(i * cellSize, sizePx),
                    strokeWidth = 5.dp.toPx(),
                    cap = StrokeCap.Round
                )
                // horizontal dividers
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, i * cellSize),
                    end = Offset(sizePx, i * cellSize),
                    strokeWidth = 5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            //endregion

            // start animation for clicked cell
            clickedCell?.let { (row, column) ->
                if (board[row][column] != null) {
                    scope.animateFloatToOne(animations[row][column])
                }
                clickedCell = null
            }

            board.forEachIndexed { rowIdx, row ->
                row.forEachIndexed { colIdx, player ->
                    val x1 = (padding + colIdx) * cellSize
                    val y1 = (padding + rowIdx) * cellSize
                    val x2 = (1f - padding + colIdx) * cellSize
                    val y2 = (1f - padding + rowIdx) * cellSize
                    val diameter = (1f - 2 * padding) * cellSize

                    if (player == Mark.O) {
                        drawArc(
                            color = Color.Green,
                            startAngle = 0f,
                            sweepAngle = animations[rowIdx][colIdx].value * 360f,
                            useCenter = false,
                            topLeft = Offset(x1, y1),
                            size = Size(diameter, diameter),
                            style = Stroke(
                                width = 5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                    } else if (player == Mark.X) {
                        val path1 = Path().apply {
                            moveTo(x1, y1)
                            lineTo(x2, y2)
                        }
                        val path2 = Path().apply {
                            moveTo(x1, y2)
                            lineTo(x2, y1)
                        }
                        val outPath1 = Path()
                        PathMeasure().apply {
                            setPath(path1, false)
                            getSegment(0f, animations[rowIdx][colIdx].value * length, outPath1)
                        }
                        val outPath2 = Path()
                        PathMeasure().apply {
                            setPath(path2, false)
                            getSegment(0f, animations[rowIdx][colIdx].value * length, outPath2)
                        }
                        drawPath(
                            path = outPath1,
                            color = Color.Red,
                            style = Stroke(
                                width = 5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                        drawPath(
                            path = outPath2,
                            color = Color.Red,
                            style = Stroke(
                                width = 5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun emptyAnimations(): ArrayList<ArrayList<Animatable<Float, AnimationVector1D>>> {
    val arrayList = arrayListOf<ArrayList<Animatable<Float, AnimationVector1D>>>()
    for (i in 0..2) {
        arrayList.add(arrayListOf())
        repeat(3) {
            arrayList[i].add(Animatable(0f))
        }
    }
    return arrayList
}

private fun CoroutineScope.animateFloatToOne(animatable: Animatable<Float, AnimationVector1D>) {
    launch {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500),
        )
    }
}

private fun detectClickedCell(size: IntSize, offset: Offset): Pair<Int, Int> {
    val dividerX1 = size.width / 3f
    val dividerX2 = 2 * size.width / 3f
    val dividerY1 = size.height / 3f
    val dividerY2 = 2 * size.height / 3f

    val row = when {
        offset.y < dividerY1 -> 0
        offset.y in dividerY1..dividerY2 -> 1
        offset.y > dividerY2 -> 2
        else -> throw IllegalArgumentException("Offset.y (${offset.y}) is out of expected range: 0..${size.height}")
    }
    val column = when {
        offset.x < dividerX1 -> 0
        offset.x in dividerX1..dividerX2 -> 1
        offset.x > dividerX2 -> 2
        else -> throw IllegalArgumentException("Offset.x (${offset.x}) is out of expected range: 0..${size.width}")
    }
    return row to column
}

@Preview
@Composable
fun BoardViewPreview() {
    BoardView(
        board = listOf(
            listOf(Mark.X, Mark.O, null),
            listOf(Mark.O, Mark.X, null),
            listOf(null, null, null)
        ),
        isGameOver = false,
        modifier = Modifier.size(300.dp),
    )
}
