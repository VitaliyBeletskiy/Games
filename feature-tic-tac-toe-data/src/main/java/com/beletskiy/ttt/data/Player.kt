package com.beletskiy.ttt.data

enum class Player {
    X, O;

    fun other(): Player = if (this == X) O else X
}
