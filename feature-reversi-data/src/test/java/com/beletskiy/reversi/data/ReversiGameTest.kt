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
        val emptyBoard = emptyBoard()
        game.setBoard(emptyBoard)
        assertTrue(compareDiscsOnBoards(game.getBoard(), emptyBoard))
    }

    @Test
    fun `newGame() returns correct GameState`() {
        val gameState = game.newGame()
        val expectedBoard = emptyBoard().apply {
            this[3][3] = Cell(disc = Disc.WHITE)
            this[4][4] = Cell(disc = Disc.WHITE)
            this[3][4] = Cell(disc = Disc.BLACK)
            this[4][3] = Cell(disc = Disc.BLACK)
        }
        assertTrue(compareDiscsOnBoards(gameState.board, expectedBoard))
        assertEquals(PlayerDisc.BLACK, gameState.currentPlayer)
        assertFalse(gameState.isGameOver)
        assertEquals(2, gameState.blackScore)
        assertEquals(2, gameState.whiteScore)
        assertEquals(null, gameState.winner)
    }

    @Test
    fun `makeMove on empty board should not change the board`() {
        game.newGame()
        val initialBoard = game.getBoard()
        val gameState = game.makeMove(0, 0)
        assertTrue(compareDiscsOnBoards(gameState.board, initialBoard))
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

        val expectedBoard = emptyBoard().apply {
            this[3][2] = Cell(disc = Disc.BLACK)
            this[3][3] = Cell(disc = Disc.BLACK)
            this[3][4] = Cell(disc = Disc.BLACK)
            this[4][3] = Cell(disc = Disc.BLACK)
            this[4][4] = Cell(disc = Disc.WHITE)
        }
        assertTrue(compareDiscsOnBoards(gameState.board, expectedBoard))
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

        val expectedBoard = emptyBoard().apply {
            this[3][2] = Cell(disc = Disc.BLACK)
            this[3][3] = Cell(disc = Disc.BLACK)
            this[4][3] = Cell(disc = Disc.BLACK)
            this[2][4] = Cell(disc = Disc.WHITE)
            this[3][4] = Cell(disc = Disc.WHITE)
            this[4][4] = Cell(disc = Disc.WHITE)
        }
        assertTrue(compareDiscsOnBoards(gameState.board, expectedBoard))
    }

    @Test
    fun `if opponent has no moves the player can make a move again`() {
        game.newGame()
        val board = emptyBoard()
        board[0][0] = Cell(disc = Disc.BLACK)
        board[1][0] = Cell(disc = Disc.BLACK)
        board[0][1] = Cell(disc = Disc.WHITE)
        board[1][1] = Cell(disc = Disc.WHITE)
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
        val board = emptyBoard()
        board[0][0] = Cell(disc = Disc.BLACK)
        board[1][0] = Cell(disc = Disc.BLACK)
        board[0][1] = Cell(disc = Disc.WHITE)
        board[1][1] = Cell(disc = Disc.WHITE)
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
        val possibleMoves = gameState.board.flatMapIndexed { rowIndex, row ->
            row.mapIndexedNotNull { colIndex, cell ->
                if (cell.moveOption == MoveOption.POSSIBLE) 1 else null
            }
        }.sumOf {
            it
        }
        assertEquals(4, possibleMoves)
        assertEquals(MoveOption.POSSIBLE, gameState.board[3][2].moveOption)
        assertEquals(MoveOption.POSSIBLE, gameState.board[2][3].moveOption)
        assertEquals(MoveOption.POSSIBLE, gameState.board[4][5].moveOption)
        assertEquals(MoveOption.POSSIBLE, gameState.board[5][4].moveOption)
    }


    private fun emptyBoard(): MutableList<MutableList<Cell>> {
        return MutableList(8) { MutableList(8) { Cell(disc = Disc.NONE) } }
    }

    private fun compareDiscsOnBoards(board1: List<List<Cell>>, board2: List<List<Cell>>): Boolean {
        if (board1.size != board2.size) return false
        for (i in board1.indices) {
            if (board1[i].size != board2[i].size) return false
            for (j in board1[i].indices) {
                if (board1[i][j].disc != board2[i][j].disc) return false
            }
        }
        return true
    }
}
