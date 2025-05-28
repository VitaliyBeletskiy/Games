package com.beletskiy.reversi.data

enum class PlayerDisc {
    BLACK, WHITE;

    fun opposite(): PlayerDisc = if (this == BLACK) WHITE else BLACK

    fun toDisc(): Disc = when (this) {
        BLACK -> Disc.BLACK
        WHITE -> Disc.WHITE
    }
}
