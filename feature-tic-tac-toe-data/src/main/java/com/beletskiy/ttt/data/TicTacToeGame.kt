package com.beletskiy.ttt.data

interface ITicTacToeGame {
    fun newGame(mark: Mark? = null): GameState
    fun makeMove(row: Int, column: Int): GameState?

    companion object {
        fun getInstance() = TicTacToeGameImpl()
    }
}

class TicTacToeGameImpl : ITicTacToeGame {
    private val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { null as Mark? } }
    private var currentMark = Mark.X
    private var isGameOver = false

    override fun newGame(mark: Mark?): GameState {
        currentMark = mark ?: Mark.X
        board.forEach { it.fill(null) }
        isGameOver = false
        return GameState(currentMark = currentMark)
    }

    override fun makeMove(row: Int, column: Int): GameState? {
        if (isGameOver || board[row][column] != null) {
            return null
        }

        board[row][column] = currentMark
        val winner = if (checkIfWin(currentMark)) currentMark else null
        val isDraw = checkIfDraw()
        isGameOver = winner != null || isDraw
        currentMark = currentMark.other()

        return GameState(
            currentMark = currentMark,
            board = board.toList(),
            winner = winner,
            isDraw = isDraw,
            isGameOver = isGameOver,
        )
    }

    @Suppress("ReturnCount")
    private fun checkIfWin(mark: Mark): Boolean {
        // Check rows
        if (board.any { row -> row.all { it == mark } }) return true
        // Check columns
        for (col in 0..2) {
            if ((0..2).all { row -> board[row][col] == mark }) return true
        }
        // Check diagonals
        if ((0..2).all { board[it][it] == mark }) return true
        if ((0..2).all { board[it][2 - it] == mark }) return true

        return false
    }

    private fun checkIfDraw(): Boolean {
        return board.all { row -> row.all { it != null } }
    }

    private fun Array<Array<Mark?>>.toList(): List<List<Mark?>> =
        this.map { it.toList() }
}
