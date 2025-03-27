package com.beletskiy.bac.ui.utils

import com.beletskiy.bac.ui.R
import com.beletskiy.bullscows.GuessResult

fun numberToDrawable(number: Int): Int = when (number) {
    0 -> R.drawable.ic_0
    1 -> R.drawable.ic_1
    2 -> R.drawable.ic_2
    3 -> R.drawable.ic_3
    4 -> R.drawable.ic_4
    5 -> R.drawable.ic_5
    6 -> R.drawable.ic_6
    7 -> R.drawable.ic_7
    8 -> R.drawable.ic_8
    9 -> R.drawable.ic_9
    else -> throw IllegalArgumentException("Drawable for number $number not found")
}

fun guessResultToDrawable(guessResult: GuessResult): Int = when (guessResult) {
    GuessResult.BULL -> R.drawable.ic_bull
    GuessResult.COW -> R.drawable.ic_cow
    GuessResult.NOTHING -> R.drawable.ic_nothing
}
