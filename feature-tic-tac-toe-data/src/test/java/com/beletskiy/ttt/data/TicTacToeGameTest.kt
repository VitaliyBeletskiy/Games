package com.beletskiy.ttt.data

import org.junit.Before
import org.junit.Test

class TicTacToeGameTest {

    private lateinit var ticTacToeGame: ITicTacToeGame

    @Before
    fun setUp() {
        ticTacToeGame = TicTacToeGameImpl()
    }

    //region newGame tests
    @Test
    fun `when newGame() is called then currentMark is X`() {
        val gameState = ticTacToeGame.newGame(gameType = GameType.Classic)
        assert(gameState.currentMark == Mark.X)
    }

    @Test
    fun `when newGame(Player_O) is called then currentMark is O`() {
        val gameState = ticTacToeGame.newGame(gameType = GameType.Classic, Mark.O)
        assert(gameState.currentMark == Mark.O)
    }

    @Test
    fun `when newGame(Player_X) is called then currentMark is X`() {
        val gameState = ticTacToeGame.newGame(gameType = GameType.Classic, Mark.O)
        assert(gameState.currentMark == Mark.O)
    }

    @Test
    fun `when newGame() is called then board is empty`() {
        val gameState = ticTacToeGame.newGame(gameType = GameType.Classic)
        assert(gameState.board.all { row -> row.all { it == null } })
    }

    @Test
    fun `when newGame() is called then winner is null`() {
        val gameState = ticTacToeGame.newGame(gameType = GameType.Classic)
        assert(gameState.winner == null)
    }

    @Test
    fun `when newGame() is called then isDraw is false`() {
        val gameState = ticTacToeGame.newGame(gameType = GameType.Classic)
        assert(gameState.isDraw == false)
    }

    @Test
    fun `when newGame() is called then isGameOver is false`() {
        val gameState = ticTacToeGame.newGame(gameType = GameType.Classic)
        assert(gameState.isGameOver == false)
    }
    //endregion

    //region makeMove tests
    @Test
    fun `when makeMove() is called then board is updated`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        val gameState = ticTacToeGame.makeMove(0, 0)
        assert(gameState?.board?.get(0)?.get(0) == Mark.X)
    }

    @Test
    fun `when makeMove() is called then currentMark is updated`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        val gameState = ticTacToeGame.makeMove(0, 0)
        assert(gameState?.currentMark == Mark.O)
    }

    @Test
    fun `when makeMove() is called on occupied cell then board is not updated`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0)
        val gameState = ticTacToeGame.makeMove(0, 0)
        assert(gameState == null)
    }
    //endregion

    //region when it's Draw
    @Test
    fun `when all cells are filled and no one wins then isDraw is true`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(0, 1) // O
        ticTacToeGame.makeMove(1, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(2, 1) // X
        ticTacToeGame.makeMove(2, 0) // O
        ticTacToeGame.makeMove(0, 2) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameState = ticTacToeGame.makeMove(2, 2) // X
        assert(gameState?.isDraw == true)
    }

    @Test
    fun `when all cells are filled and no one wins then isGameOver is true`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(0, 1) // O
        ticTacToeGame.makeMove(1, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(2, 1) // X
        ticTacToeGame.makeMove(2, 0) // O
        ticTacToeGame.makeMove(0, 2) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameState = ticTacToeGame.makeMove(2, 2) // X
        assert(gameState?.isGameOver == true)
    }
    //endregion

    //region when it's Win
    @Test
    fun `when makeMove() is called and it's a win then winner is set`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameState = ticTacToeGame.makeMove(0, 2) // X
        assert(gameState?.winner == Mark.X)
    }

    @Test
    fun `when it's a win then isDraw is false`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameState = ticTacToeGame.makeMove(0, 2) // X
        assert(gameState?.isDraw == false)
    }

    @Test
    fun `when it's a win then board is not updated`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameState = ticTacToeGame.makeMove(0, 2) // X
        assert(gameState?.board?.get(0)?.get(2) == Mark.X)
    }

    @Test
    fun `when it's a win then winner is not null`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 2) // O
        val gameState = ticTacToeGame.makeMove(0, 2) // X
        assert(gameState?.winner != null)
    }

    @Test
    fun `when the X-mark wins then winner is X`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 0) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 1) // O
        val gameState = ticTacToeGame.makeMove(0, 2) // X
        assert(gameState?.winner == Mark.X)
    }

    @Test
    fun `when the O-mark wins then winner is O`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(0, 0) // O
        ticTacToeGame.makeMove(0, 2) // X
        ticTacToeGame.makeMove(1, 1) // O
        ticTacToeGame.makeMove(1, 2) // X
        val gameState = ticTacToeGame.makeMove(2, 2) // O
        assert(gameState?.winner == Mark.O)
    }

    @Test
    fun `when it's a win then isGameOver is true`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(1, 0) // O
        ticTacToeGame.makeMove(0, 1) // X
        ticTacToeGame.makeMove(1, 1) // O
        val gameState = ticTacToeGame.makeMove(0, 2) // X
        assert(gameState?.isGameOver == true)
    }

    @Test
    fun `when Player X wins by filling the last cell then isDraw == false`() {
        ticTacToeGame.newGame(gameType = GameType.Classic)
        ticTacToeGame.makeMove(0, 0) // X
        ticTacToeGame.makeMove(0, 2) // O
        ticTacToeGame.makeMove(1, 1) // X
        ticTacToeGame.makeMove(0, 1) // O
        ticTacToeGame.makeMove(1, 2) // X
        ticTacToeGame.makeMove(1, 0) // O
        ticTacToeGame.makeMove(2, 0) // X
        ticTacToeGame.makeMove(2, 1) // O
        val gameState = ticTacToeGame.makeMove(2, 2) // X
        assert(gameState?.isDraw == false)
        assert(gameState?.isGameOver == true)
        assert(gameState?.winner != null)
    }
    //endregion
}
