package com.beletskiy.fifteen.data

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class FifteenGameImplTest {

    private lateinit var game: FifteenGameImpl

    @Before
    fun setUp() {
        game = FifteenGameImpl()
    }

    @Test
    fun `when new game then game must not be over`() {
        val state = game.newGame()
        assertFalse(state.isGameOver)
    }

    @Test
    fun `compareBoards() should return true for the same board`() {
        val board = solvedBoard()
        game.setBoard(board)
        assertTrue(game.compareBoards(board))
    }

    @Test
    fun `compareBoards() should return false for different boards`() {
        val board = solvedBoard()
        game.setBoard(board)
        val modified = board.map { it.toMutableList() }
        modified[3][2] = 0
        modified[3][3] = 15
        assertFalse(game.compareBoards(modified))
    }

    @Test
    fun `makeMove(SINGLE_TILE) should work for a valid move`() {
        val board = boardWithEmptyAt(3, 2)
        game.setBoard(board)
        val state = game.makeMove(3, 3, MoveType.SINGLE_TILE)
        assertEquals(15, state.board[3][2])
        assertEquals(0, state.board[3][3])
    }

    @Test
    fun `makeMove(SINGLE_TILE) should not work for an invalid move`() {
        val board = boardWithEmptyAt(3, 2)
        game.setBoard(board)
        game.makeMove(0, 0, MoveType.SINGLE_TILE)
        assertTrue(game.compareBoards(board))
    }

    @Test
    fun `makeMove(multiTile) should shift tiles vertically and horizontally`() {
        game.setBoard(solvedBoard())

        game.makeMove(0, 3, MoveType.MULTI_TILE)
        assertTrue(
            game.compareBoards(
                listOf(
                    listOf(1, 2, 3, 0),
                    listOf(5, 6, 7, 4),
                    listOf(9, 10, 11, 8),
                    listOf(13, 14, 15, 12)
                )
            )
        )

        game.makeMove(0, 0, MoveType.MULTI_TILE)
        assertTrue(
            game.compareBoards(
                listOf(
                    listOf(0, 1, 2, 3),
                    listOf(5, 6, 7, 4),
                    listOf(9, 10, 11, 8),
                    listOf(13, 14, 15, 12)
                )
            )
        )

        game.makeMove(3, 0, MoveType.MULTI_TILE)
        assertTrue(
            game.compareBoards(
                listOf(
                    listOf(5, 1, 2, 3),
                    listOf(9, 6, 7, 4),
                    listOf(13, 10, 11, 8),
                    listOf(0, 14, 15, 12)
                )
            )
        )

        game.makeMove(3, 3, MoveType.MULTI_TILE)
        assertTrue(
            game.compareBoards(
                listOf(
                    listOf(5, 1, 2, 3),
                    listOf(9, 6, 7, 4),
                    listOf(13, 10, 11, 8),
                    listOf(14, 15, 12, 0)
                )
            )
        )
    }

    @Test
    fun `makeMove(multiTile) should not move if move is invalid`() {
        val board = boardWithEmptyAt(3, 2)
        game.setBoard(board)
        game.makeMove(0, 0, MoveType.MULTI_TILE)
        assertTrue(game.compareBoards(board))
    }

    @Test
    fun `when game is solved then isGameOver should be true`() {
        game.setBoard(listOf(
            listOf(1, 2, 3, 4),
            listOf(5, 6, 7, 8),
            listOf(9, 10, 11, 12),
            listOf(13, 14, 0, 15)
        ))
        val state = game.makeMove(3, 3, MoveType.SINGLE_TILE)
        assertTrue(state.isGameOver)
    }

    private fun solvedBoard(): List<List<Int>> {
        return listOf(
            listOf(1, 2, 3, 4),
            listOf(5, 6, 7, 8),
            listOf(9, 10, 11, 12),
            listOf(13, 14, 15, 0)
        )
    }

    private fun boardWithEmptyAt(row: Int, col: Int): List<List<Int>> {
        val board = solvedBoard().map { it.toMutableList() }
        board[3][3] = board[row][col]
        board[row][col] = 0
        return board
    }
}
