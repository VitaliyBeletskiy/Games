package com.beletskiy.ttt.data

class FakeTicTacToeGame : ITicTacToeGame {

    override fun newGame(mark: Mark?): GameState {
        return GameState(
            currentMark = Mark.X,
            board = List(3) { List(3) { null } },
            winner = null,
            isDraw = false,
        )
    }

    override fun makeMove(row: Int, column: Int): GameState? {
        return null
    }
}
