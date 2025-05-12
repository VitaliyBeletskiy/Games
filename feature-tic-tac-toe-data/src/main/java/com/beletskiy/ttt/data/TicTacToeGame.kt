package com.beletskiy.ttt.data

private const val MAX_MARKS = 3

typealias Position = Pair<Int, Int>

interface ITicTacToeGame {
    fun newGame(gameType: GameType, mark: Mark? = null): GameState
    fun makeMove(row: Int, column: Int): GameState?

    companion object {
        fun getInstance() = TicTacToeGameImpl()
    }
}

class TicTacToeGameImpl : ITicTacToeGame {
    private val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { null as Mark? } }
    private var currentMark = Mark.X
    private var isGameOver = false
    private var gameType = GameType.Classic
    val xPositions = mutableListOf<Position>()
    val oPositions = mutableListOf<Position>()

    override fun newGame(gameType: GameType, mark: Mark?): GameState {
        this.gameType = gameType
        currentMark = mark ?: Mark.X
        board.forEach { it.fill(null) }
        xPositions.clear()
        oPositions.clear()
        isGameOver = false
        return GameState(currentMark = currentMark)
    }

    override fun makeMove(row: Int, column: Int): GameState? {
        if (isGameOver || board[row][column] != null) {
            return null
        }

        if (gameType == GameType.Dynamic) {
            if (currentMark == Mark.X) {
                xPositions.add(row to column)
                if (xPositions.size > MAX_MARKS) {
                    board[xPositions[0].first][xPositions[0].second] = null
                    xPositions.removeAt(0)
                }
            } else {
                oPositions.add(row to column)
                if (oPositions.size > MAX_MARKS) {
                    board[oPositions[0].first][oPositions[0].second] = null
                    oPositions.removeAt(0)
                }
            }
        }

        board[row][column] = currentMark
        val winner = if (checkIfWin(currentMark)) currentMark else null
        val isDraw = winner?.let { false } ?: checkIfDraw()
        isGameOver = winner != null || isDraw
        currentMark = currentMark.other()

        return GameState(
            currentMark = currentMark,
            board = board.toList(),
            winner = winner,
            isDraw = isDraw,
            isGameOver = isGameOver,
            positionToBeRemoved = getPositionToBeRemoved(),
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

    private fun getPositionToBeRemoved(): Position? {
        return when (gameType) {
            GameType.Classic -> null
            GameType.Dynamic -> {
                if (currentMark == Mark.X && xPositions.size == MAX_MARKS) {
                    xPositions[0]
                } else if (currentMark == Mark.O && oPositions.size == MAX_MARKS) {
                    oPositions[0]
                } else {
                    null
                }
            }
        }
    }

    private fun Array<Array<Mark?>>.toList(): List<List<Mark?>> =
        this.map { it.toList() }
}
