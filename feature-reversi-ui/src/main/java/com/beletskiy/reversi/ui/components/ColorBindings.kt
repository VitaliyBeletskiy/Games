package com.beletskiy.reversi.ui.components

import com.beletskiy.reversi.data.PlayerDisc
import com.beletskiy.shared.theme.Accent
import  androidx.compose.ui.graphics.Color
import com.beletskiy.reversi.data.Disc

fun PlayerDisc.toColor(): Color {
    return when (this) {
        PlayerDisc.BLACK -> Accent
        PlayerDisc.WHITE -> Color.White
    }
}

fun Disc.toColor(): Color {
    return when (this) {
        Disc.BLACK -> Accent
        Disc.WHITE -> Color.White
        Disc.NONE -> Color.Transparent
    }
}
