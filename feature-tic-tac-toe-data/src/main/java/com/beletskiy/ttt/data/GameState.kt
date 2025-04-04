package com.beletskiy.ttt.data

internal const val BOARD_SIZE = 3

data class GameState(
    val currentMark: Mark,
    val board: List<List<Mark?>> = List(BOARD_SIZE) { List(BOARD_SIZE) { null } },
    val winner: Mark? = null,
    val isDraw: Boolean = false,
    val isGameOver: Boolean = false,
)
