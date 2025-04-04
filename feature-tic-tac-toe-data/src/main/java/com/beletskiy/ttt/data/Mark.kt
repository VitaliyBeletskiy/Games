package com.beletskiy.ttt.data

enum class Mark {
    X, O;

    fun other(): Mark = if (this == X) O else X
}
