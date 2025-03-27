package com.beletskiy.bullscows

data class Guess(
    val ordinal: Int,
    val userInput: List<Int>,
    val guessResults: List<GuessResult>,
)
