package com.beletskiy.ttt.data

class FakeTicTacToeGame : ITicTacToeGame {

    override fun newGame(player: Player?): GameStatus {
        return GameStatus(
            currentPlayer = Player.X,
            board = List(3) { List(3) { null } },
            winner = null,
            isDraw = false,
        )
    }

    override fun makeMove(row: Int, column: Int): GameStatus? {
        return null
    }
}
