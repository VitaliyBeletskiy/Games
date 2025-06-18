package com.beletskiy.reversi.data

data class GameState (
    val board: List<List<Disc>>,
    val currentPlayer: PlayerDisc,
    val isGameOver: Boolean,
    val blackScore: Int,
    val whiteScore: Int,
    val winner: PlayerDisc? = null,
    val possibleMoves: Set<Pair<Int, Int>> = emptySet(),
)
