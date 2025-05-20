package com.beletskiy.fifteen.data

class FakeFifteenGame : IFifteenGame {

    override fun newGame(): GameState {
        return GameState(
            board = listOf(
                listOf(1, 2, 3, 4),
                listOf(5, 6, 7, 8),
                listOf(9, 10, 11, 12),
                listOf(13, 14, 15, 0),
            ),
            isGameOver = false,
        )
    }

    override fun makeMove(
        row: Int,
        column: Int,
        moveType: MoveType,
    ): GameState {
        return GameState(
            board = listOf(
                listOf(1, 2, 3, 4),
                listOf(5, 6, 7, 8),
                listOf(9, 10, 11, 12),
                listOf(13, 14, 15, 0),
            ),
            isGameOver = false,
        )
    }
}
