package com.beletskiy.bullscows.game

data class Guess(
    val number: Int,
    val userInput: List<Int>,
    val results: List<Result>,
)
