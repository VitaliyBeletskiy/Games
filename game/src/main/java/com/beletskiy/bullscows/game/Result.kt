package com.beletskiy.bullscows.game

sealed class Result {
    data object Success : Result()
    data class Failure(val error: Exception) : Result()
}

inline fun Result.ifSuccess(block: () -> Unit) = when (this) {
    is Result.Success -> block()
    else -> {}
}

inline fun Result.ifFailure(block: (Exception) -> Unit) = when (this) {
    is Result.Failure -> block(this.error)
    else -> {}
}
