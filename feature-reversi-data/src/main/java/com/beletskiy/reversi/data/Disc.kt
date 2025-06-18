package com.beletskiy.reversi.data

enum class Disc {
    BLACK, WHITE, NONE;

    fun isOccupied() = this != NONE
}
