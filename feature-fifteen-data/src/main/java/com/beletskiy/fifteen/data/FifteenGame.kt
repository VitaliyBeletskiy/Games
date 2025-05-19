package com.beletskiy.fifteen.data

import com.beletskiy.fifteen.data.IFifteenGame.Companion.BOARD_SIZE

interface IFifteenGame {
    fun newGame(): GameState
    fun makeMove(row: Int, column: Int): GameState

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

    private val board: Array<IntArray> = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) }

    override fun newGame(): GameState {
        do {
            shuffleBoard()
        } while (isGameOver())
        return GameState(boardToList(), false)
    }

    override fun makeMove(row: Int, column: Int): GameState {
        if (!canMove(row, column)) {
            return GameState(boardToList(), isGameOver = false)
        }

        val emptyTile = findEmptyTile()
        board[emptyTile.first][emptyTile.second] = board[row][column]
        board[row][column] = 0

        val isGameOver = isGameOver()

        return GameState(boardToList(), isGameOver)
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
    private fun canMove(row: Int, column: Int): Boolean {
        val emptyTile = findEmptyTile()
        if (row == emptyTile.first) {
            return column == (emptyTile.second - 1) || column == (emptyTile.second + 1)
        } else if (column == emptyTile.second) {
            return row == (emptyTile.first - 1) || row == (emptyTile.first + 1)
        }
        return false
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
