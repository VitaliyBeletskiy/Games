package com.beletskiy.reversi.data

data class GameState (
    val board: List<List<Cell>>,
    val currentPlayer: PlayerDisc,
    val isGameOver: Boolean,
    val blackScore: Int,
    val whiteScore: Int,
    val winner: PlayerDisc? = null,
)
