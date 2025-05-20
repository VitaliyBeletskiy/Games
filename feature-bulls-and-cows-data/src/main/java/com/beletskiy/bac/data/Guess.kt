package com.beletskiy.bac.data

data class Guess(
    val ordinal: Int,
    val userInput: List<Int>,
    val guessResults: List<GuessResult>,
)
