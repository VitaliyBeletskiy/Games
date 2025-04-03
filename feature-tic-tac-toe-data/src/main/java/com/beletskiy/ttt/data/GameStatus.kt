package com.beletskiy.ttt.data

internal const val BOARD_SIZE = 3

data class GameStatus(
    val currentPlayer: Player,
    val board: List<List<Player?>> = List(BOARD_SIZE) { List(BOARD_SIZE) { null } },
    val winner: Player? = null,
    val isDraw: Boolean = false,
)
