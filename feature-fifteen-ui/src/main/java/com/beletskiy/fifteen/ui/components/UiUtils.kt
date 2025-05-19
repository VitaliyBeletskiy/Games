package com.beletskiy.fifteen.ui.components

import com.beletskiy.fifteen.ui.R

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
    10 -> R.drawable.ic_10
    11 -> R.drawable.ic_11
    12 -> R.drawable.ic_12
    13 -> R.drawable.ic_13
    14 -> R.drawable.ic_14
    15 -> R.drawable.ic_15
    else -> throw IllegalArgumentException("Drawable for number $number not found")
}
