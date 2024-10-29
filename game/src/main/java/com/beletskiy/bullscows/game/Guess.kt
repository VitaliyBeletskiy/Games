package com.beletskiy.bullscows.game

data class Guess(
    val ordinal: Int,
    val userInput: List<Int>,
    val guessResults: List<GuessResult>,
)
