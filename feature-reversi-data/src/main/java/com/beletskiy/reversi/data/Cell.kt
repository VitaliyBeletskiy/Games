package com.beletskiy.reversi.data

enum class Disc {
    BLACK, WHITE, NONE
}

enum class MoveOption {
    NONE,
    POSSIBLE
}

data class Cell(
    val disc: Disc = Disc.NONE,
    val moveOption: MoveOption = MoveOption.NONE,
) {
    fun isOccupied() = disc != Disc.NONE
}
