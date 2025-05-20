package com.beletskiy.fifteen.data

import com.beletskiy.fifteen.data.IFifteenGame.Companion.BOARD_SIZE

interface IFifteenGame {
    fun newGame(): GameState
    fun makeMove(row: Int, column: Int, moveType: MoveType = MoveType.SINGLE_TILE): GameState

    companion object {
        fun getInstance() = FifteenGameImpl()
        const val BOARD_SIZE = 4
    }
}

class FifteenGameImpl : IFifteenGame {

    companion object {
        private const val SHUFFLE_COUNT = 1_000
        private val initialBoard = listOf(
            listOf(1, 2, 3, 4),
            listOf(5, 6, 7, 8),
            listOf(9, 10, 11, 12),
            listOf(13, 14, 15, 0),
        )
    }

    private enum class Vector {
        NORTH, EAST, SOUTH, WEST
    }

    private val board: Array<IntArray> = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) }

    override fun newGame(): GameState {
        do {
            shuffleBoard()
        } while (isGameOver())
        return GameState(boardToList(), false)
    }

    @Suppress("detekt:ReturnCount")
    override fun makeMove(row: Int, column: Int, moveType: MoveType): GameState {
        if (moveType == MoveType.SINGLE_TILE && !canMoveSingle(row, column)) {
            return GameState(boardToList(), isGameOver = false)
        } else if (moveType == MoveType.MULTI_TILE && !canMoveMany(row, column)) {
            return GameState(boardToList(), isGameOver = false)
        }

        when (moveType) {
            MoveType.SINGLE_TILE -> moveSingleTile(row, column)
            MoveType.MULTI_TILE -> moveMultiTiles(row, column)
        }
        val isGameOver = isGameOver()
        return GameState(boardToList(), isGameOver)
    }

    private fun moveMultiTiles(row: Int, column: Int) {
        val emptyTile = findEmptyTile()
        val vector = when {
            row == emptyTile.first && column > emptyTile.second -> Vector.WEST
            row == emptyTile.first && column < emptyTile.second -> Vector.EAST
            column == emptyTile.second && row > emptyTile.first -> Vector.NORTH
            column == emptyTile.second && row < emptyTile.first -> Vector.SOUTH
            else -> error("Invalid row and column")
        }
        when (vector) {
            Vector.EAST -> {
                for (i in emptyTile.second downTo column + 1) {
                    board[emptyTile.first][i] = board[emptyTile.first][i - 1]
                }
            }

            Vector.WEST -> {
                for (i in emptyTile.second until column) {
                    board[emptyTile.first][i] = board[emptyTile.first][i + 1]
                }
            }

            Vector.SOUTH -> {
                for (i in emptyTile.first downTo row + 1) {
                    board[i][emptyTile.second] = board[i - 1][emptyTile.second]
                }
            }

            Vector.NORTH -> {
                for (i in emptyTile.first until row) {
                    board[i][emptyTile.second] = board[i + 1][emptyTile.second]
                }
            }
        }
        board[row][column] = 0
    }

    private fun moveSingleTile(row: Int, column: Int) {
        val emptyTile = findEmptyTile()
        board[emptyTile.first][emptyTile.second] = board[row][column]
        board[row][column] = 0
    }

    private fun isGameOver(): Boolean {
        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                if (board[i][j] != initialBoard[i][j]) return false
            }
        }
        return true
    }

    @Suppress("detekt:ReturnCount")
    private fun canMoveSingle(row: Int, column: Int): Boolean {
        val emptyTile = findEmptyTile()
        if (row == emptyTile.first) {
            return column == (emptyTile.second - 1) || column == (emptyTile.second + 1)
        } else if (column == emptyTile.second) {
            return row == (emptyTile.first - 1) || row == (emptyTile.first + 1)
        }
        return false
    }

    private fun canMoveMany(row: Int, column: Int): Boolean {
        val emptyTile = findEmptyTile()
        return (row == emptyTile.first) || (column == emptyTile.second)
    }

    private fun findEmptyTile(): Pair<Int, Int> {
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == 0) {
                    return i to j
                }
            }
        }
        error("No empty tile found")
    }

    private fun findAllNeighbors(row: Int, column: Int): List<Pair<Int, Int>> {
        val neighbors = mutableListOf<Pair<Int, Int>>()
        neighbors.add(row to column - 1)
        neighbors.add(row to column + 1)
        neighbors.add(row - 1 to column)
        neighbors.add(row + 1 to column)
        return neighbors.filter { it.first in 0 until BOARD_SIZE && it.second in 0 until BOARD_SIZE }
    }

    private fun shuffleBoard() {
        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                board[i][j] = initialBoard[i][j]
            }
        }

        repeat(SHUFFLE_COUNT) {
            val emptyTile = findEmptyTile()
            val randomNeighbor = findAllNeighbors(emptyTile.first, emptyTile.second).random()
            board[emptyTile.first][emptyTile.second] =
                board[randomNeighbor.first][randomNeighbor.second]
            board[randomNeighbor.first][randomNeighbor.second] = 0
        }
    }

    private fun boardToList(): List<List<Int>> {
        return board.map { it.toList() }
    }
}
