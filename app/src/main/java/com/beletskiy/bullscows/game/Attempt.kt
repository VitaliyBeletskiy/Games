package com.beletskiy.bullscows.game

data class Attempt(
    val attemptNumber: Int,
    val attemptValues: List<Int>,
    val attemptResults: List<Result>
) {
    enum class Result(val value: Int) {
        NOTHING(0),
        COW(1),
        BULL(2),
    }
}