package com.beletskiy.ttt.data

interface ITicTacToeGame {
    fun newGame(player: Player? = null): GameStatus
    fun makeMove(row: Int, column: Int): GameStatus?

    companion object {
        fun getInstance() = TicTacToeGameImpl()
    }
}

class TicTacToeGameImpl : ITicTacToeGame {
    private val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { null as Player? } }
    private var currentPlayer = Player.X
    private var isGameOver = false

    override fun newGame(player: Player?): GameStatus {
        currentPlayer = player ?: Player.X
        board.forEach { it.fill(null) }
        isGameOver = false
        return GameStatus(currentPlayer = currentPlayer)
    }

    @Suppress("ReturnCount")
    override fun makeMove(row: Int, column: Int): GameStatus? {
        if (isGameOver || board[row][column] != null) {
            return null
        }

        board[row][column] = currentPlayer

        if (checkIfWin(currentPlayer)) {
            isGameOver = true
            return GameStatus(
                currentPlayer = currentPlayer,
                board = board.toList(),
                winner = currentPlayer,
                isDraw = false
            )
        }
        if (checkIfDraw()) {
            isGameOver = true
            return GameStatus(currentPlayer = currentPlayer, board = board.toList(), isDraw = true)
        }
        currentPlayer = currentPlayer.other()

        return GameStatus(currentPlayer = currentPlayer, board = board.toList())
    }

    @Suppress("ReturnCount")
    private fun checkIfWin(player: Player): Boolean {
        // Check rows
        if (board.any { row -> row.all { it == player } }) return true
        // Check columns
        for (col in 0..2) {
            if ((0..2).all { row -> board[row][col] == player }) return true
        }
        // Check diagonals
        if ((0..2).all { board[it][it] == player }) return true
        if ((0..2).all { board[it][2 - it] == player }) return true

        return false
    }

    private fun checkIfDraw(): Boolean {
        return board.all { row -> row.all { it != null } }
    }

    private fun Array<Array<Player?>>.toList(): List<List<Player?>> =
        this.map { it.toList() }
}
