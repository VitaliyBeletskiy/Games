package com.beletskiy.fifteen.data

import com.beletskiy.fifteen.data.IFifteenGame.Companion.BOARD_SIZE

data class GameState(
    val board: List<List<Int>> = List(BOARD_SIZE) { List(BOARD_SIZE) { 0 } },
    val isGameOver: Boolean = false,
)
