package com.beletskiy.reversi.data

class FakeReversiGame: IReversiGame {
    override fun newGame(): GameState {
        return GameState(
            board = makeInitialBoard(),
            currentPlayer = PlayerDisc.BLACK,
            isGameOver = false,
            blackScore = 0,
            whiteScore = 0,
            winner = null,
        )
    }

    override fun makeMove(
        row: Int,
        column: Int,
    ): GameState {
        return GameState(
            board = makeInitialBoard(),
            currentPlayer = PlayerDisc.WHITE,
            isGameOver = false,
            blackScore = 0,
            whiteScore = 0,
            winner = null,
        )
    }

    private fun makeInitialBoard(): List<List<Disc>> {
        return List(IReversiGame.BOARD_SIZE) { row ->
            List(IReversiGame.BOARD_SIZE) { col ->
                when {
                    (row == 3 && col == 3) || (row == 4 && col == 4) -> Disc.WHITE
                    (row == 3 && col == 4) || (row == 4 && col == 3) -> Disc.BLACK
                    else -> Disc.NONE
                }
            }
        }
    }
}