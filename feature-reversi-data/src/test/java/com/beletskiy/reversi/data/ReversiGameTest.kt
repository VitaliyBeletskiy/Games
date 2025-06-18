package com.beletskiy.reversi.data

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before

class ReversiGameTest {

    private lateinit var game: ReversiGameImpl

    @Before
    fun setUp() {
        game = ReversiGameImpl()
    }

    @Test
    fun `printBoard() works`() {
        game.printBoard()
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `setBoard() works`() {
        val emptyBoard = makeEmptyBoard()
        game.setBoard(emptyBoard)
        assertTrue(compareBoards(game.getBoard(), emptyBoard))
    }

    @Test
    fun `newGame() returns correct GameState`() {
        val gameState = game.newGame()
        val expectedBoard = makeInitialBoard()
        assertTrue(compareBoards(gameState.board, expectedBoard))
        assertEquals(PlayerDisc.BLACK, gameState.currentPlayer)
        assertFalse(gameState.isGameOver)
        assertEquals(2, gameState.blackScore)
        assertEquals(2, gameState.whiteScore)
        assertEquals(null, gameState.winner)
        assertEquals(4, gameState.possibleMoves.size)
    }

    @Test
    fun `makeMove on empty board should not change the board`() {
        game.newGame()
        val initialBoard = makeInitialBoard()
        val gameState = game.makeMove(0, 0)
        assertTrue(compareBoards(gameState.board, initialBoard))
        assertEquals(PlayerDisc.BLACK, gameState.currentPlayer)
        assertFalse(gameState.isGameOver)
        assertEquals(2, gameState.blackScore)
        assertEquals(2, gameState.whiteScore)
    }

    @Test
    fun `after first move GameState is correct`() {
        game.newGame()
        val gameState = game.makeMove(3, 2)
        assertEquals(PlayerDisc.WHITE, gameState.currentPlayer)
        assertFalse(gameState.isGameOver)
        assertEquals(4, gameState.blackScore)
        assertEquals(1, gameState.whiteScore)

        val expectedBoard = makeEmptyBoard().apply {
            this[3][2] = Disc.BLACK
            this[3][3] = Disc.BLACK
            this[3][4] = Disc.BLACK
            this[4][3] = Disc.BLACK
            this[4][4] = Disc.WHITE
        }
        assertTrue(compareBoards(gameState.board, expectedBoard))
    }

    @Test
    fun `after first and second move GameState is correct`() {
        game.newGame()
        game.makeMove(3, 2)
        val gameState = game.makeMove(2, 4)
        assertEquals(PlayerDisc.BLACK, gameState.currentPlayer)
        assertFalse(gameState.isGameOver)
        assertEquals(3, gameState.blackScore)
        assertEquals(3, gameState.whiteScore)

        val expectedBoard = makeEmptyBoard().apply {
            this[3][2] = Disc.BLACK
            this[3][3] = Disc.BLACK
            this[4][3] = Disc.BLACK
            this[2][4] = Disc.WHITE
            this[3][4] = Disc.WHITE
            this[4][4] = Disc.WHITE
        }
        assertTrue(compareBoards(gameState.board, expectedBoard))
    }

    @Test
    fun `if opponent has no moves the player can make a move again`() {
        game.newGame()
        val board = makeEmptyBoard()
        board[0][0] = Disc.BLACK
        board[1][0] = Disc.BLACK
        board[0][1] = Disc.WHITE
        board[1][1] = Disc.WHITE
        game.setBoard(board)
        val gameState = game.makeMove(0, 2)
        assertEquals(PlayerDisc.BLACK, gameState.currentPlayer)
        assertFalse(gameState.isGameOver)
        assertEquals(4, gameState.blackScore)
        assertEquals(1, gameState.whiteScore)
    }

    @Test
    fun `if there are no moves then the game is over`() {
        game.newGame()
        val board = makeEmptyBoard().apply {
            this[0][0] = Disc.BLACK
            this[1][0] = Disc.BLACK
            this[0][1] = Disc.WHITE
            this[1][1] = Disc.WHITE
        }
        game.setBoard(board)
        game.makeMove(0, 2)
        val gameState = game.makeMove(1, 2)
        assertTrue(gameState.isGameOver)
        assertEquals(6, gameState.blackScore)
        assertEquals(0, gameState.whiteScore)
        assertEquals(PlayerDisc.BLACK, gameState.winner)
    }

    @Test
    fun `newGame() returns correct positions for possible moves`() {
        val gameState = game.newGame()
        assertEquals(4, gameState.possibleMoves.size)
        assertTrue(gameState.possibleMoves.contains(3 to 2))
        assertTrue(gameState.possibleMoves.contains(2 to 3))
        assertTrue(gameState.possibleMoves.contains(4 to 5))
        assertTrue(gameState.possibleMoves.contains(5 to 4))
    }

    private fun makeEmptyBoard(): MutableList<MutableList<Disc>> {
        return MutableList(8) { MutableList(8) { Disc.NONE } }
    }

    private fun makeInitialBoard(): List<List<Disc>> {
        return makeEmptyBoard().apply {
            this[3][3] = Disc.WHITE
            this[4][4] = Disc.WHITE
            this[3][4] = Disc.BLACK
            this[4][3] = Disc.BLACK
        }
    }

    private fun compareBoards(board1: List<List<Disc>>, board2: List<List<Disc>>): Boolean {
        if (board1.size != board2.size) return false
        for (i in board1.indices) {
            if (board1[i].size != board2[i].size) return false
            for (j in board1[i].indices) {
                if (board1[i][j] != board2[i][j]) return false
            }
        }
        return true
    }
}
