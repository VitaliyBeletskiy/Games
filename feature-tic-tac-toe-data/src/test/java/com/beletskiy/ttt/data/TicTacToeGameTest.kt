package com.beletskiy.ttt.data

import org.junit.Before
import org.junit.Test

class TicTacToeGameTest {

    private lateinit var ticTacToeGame: ITicTacToeGame

    @Before
    fun setUp() {
        ticTacToeGame = TicTacToeGameImpl()
    }

    @Test
    fun `when newGame() is called then currentPlayer is X`() {
        val gameStatus = ticTacToeGame.newGame()
        assert(gameStatus.currentPlayer == Player.X)
    }

    @Test
    fun `when newGame(Player_O) is called then currentPlayer is O`() {
        val gameStatus = ticTacToeGame.newGame(Player.O)
        assert(gameStatus.currentPlayer == Player.O)
    }

    @Test
    fun `when newGame(Player_X) is called then currentPlayer is X`() {
        val gameStatus = ticTacToeGame.newGame(Player.O)
        assert(gameStatus.currentPlayer == Player.O)
    }

    @Test
    fun `when newGame() is called then board is empty`() {
        val gameStatus = ticTacToeGame.newGame()
        assert(gameStatus.board.all { row -> row.all { it == null } })
    }

    @Test
    fun `when newGame() is called then winner is null`() {
        val gameStatus = ticTacToeGame.newGame()
        assert(gameStatus.winner == null)
    }

    @Test
    fun `when newGame() is called then isDraw is false`() {
        val gameStatus = ticTacToeGame.newGame()
        assert(gameStatus.isDraw == false)
    }

    @Test
    fun `when makeMove() is called then board is updated`() {
        ticTacToeGame.newGame()
        val gameStatus = ticTacToeGame.makeMove(0, 0)
        assert(gameStatus?.board?.get(0)?.get(0) == Player.X)
    }

    @Test
    fun `when makeMove() is called then currentPlayer is updated`() {
        ticTacToeGame.newGame()
        val gameStatus = ticTacToeGame.makeMove(0, 0)
        assert(gameStatus?.currentPlayer == Player.O)
    }

    @Test
    fun `when makeMove() is called on occupied cell then board is not updated`() {
        ticTacToeGame.newGame()
        ticTacToeGame.makeMove(0, 0)
        val gameStatus = ticTacToeGame.makeMove(0, 0)
        assert(gameStatus == null)
    }

    @Test
    fun `when makeMove() is called and player wins then winner is set`() {
        ticTacToeGame.newGame()
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameStatus = ticTacToeGame.makeMove(0, 2) // X
        assert(gameStatus?.winner == Player.X)
    }

    @Test
    fun `when makeMove() is called and all cells are filled then isDraw is true`() {
        ticTacToeGame.newGame()
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(0, 1) // O
        ticTacToeGame.makeMove(1, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(2, 1) // X
        ticTacToeGame.makeMove(2, 0) // O
        ticTacToeGame.makeMove(0, 2) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameStatus = ticTacToeGame.makeMove(2, 2) // X
        assert(gameStatus?.isDraw == true)
    }

    @Test
    fun `when makeMove() is called and player wins then isDraw is false`() {
        ticTacToeGame.newGame()
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameStatus = ticTacToeGame.makeMove(0, 2) // X
        assert(gameStatus?.isDraw == false)
    }

    @Test
    fun `when makeMove() is called and player wins then board is not updated`() {
        ticTacToeGame.newGame()
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameStatus = ticTacToeGame.makeMove(0, 2) // X
        assert(gameStatus?.board?.get(0)?.get(2) == Player.X)
    }

    @Test
    fun `when makeMove() is called and player wins then winner is not null`() {
        ticTacToeGame.newGame()
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameStatus = ticTacToeGame.makeMove(0, 2) // X
        assert(gameStatus?.winner != null)
    }

    @Test
    fun `when makeMove() is called and X player wins then winner is X`() {
        ticTacToeGame.newGame()
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 0) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 1) // O
        val gameStatus = ticTacToeGame.makeMove(0, 2) // X
        assert(gameStatus?.winner == Player.X)
    }

    @Test
    fun `when makeMove() is called and O player wins then winner is O`() {
        ticTacToeGame.newGame()
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(0, 0) // O
        ticTacToeGame.makeMove(0, 2) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(1, 2) // X
        val gameStatus = ticTacToeGame.makeMove(2, 2) // O
        assert(gameStatus?.winner == Player.O)
    }
}
