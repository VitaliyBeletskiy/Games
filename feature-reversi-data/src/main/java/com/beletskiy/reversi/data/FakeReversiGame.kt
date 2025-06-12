package com.beletskiy.reversi.data

class FakeReversiGame: IReversiGame {
    override fun newGame(): GameState {
        return GameState(
            board = listOf(
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(disc = Disc.BLACK), Cell(disc = Disc.WHITE), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(disc = Disc.WHITE), Cell(disc = Disc.BLACK), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
            ),
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
            board = listOf(
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(disc = Disc.BLACK), Cell(disc = Disc.WHITE), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(disc = Disc.WHITE), Cell(disc = Disc.BLACK), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(), Cell(disc = Disc.BLACK), Cell(), Cell(), Cell()),
                listOf(Cell(), Cell(), Cell(), Cell(disc = Disc.WHITE), Cell(disc = Disc.BLACK), Cell(), Cell(), Cell()),
                listOf(Cell(disc = Disc.WHITE), Cell(disc = Disc.BLACK), Cell(disc = Disc.WHITE),
                       Cell(disc = Disc.BLACK),  Cell(disc = Disc.WHITE),  Cell(disc = Disc.BLACK),
                       Cell(disc = Disc.WHITE),  Cell(disc = Disc.BLACK)),
            ),
            currentPlayer = PlayerDisc.WHITE,
            isGameOver = false,
            blackScore = 0,
            whiteScore = 0,
            winner = null,
        )
    }
}