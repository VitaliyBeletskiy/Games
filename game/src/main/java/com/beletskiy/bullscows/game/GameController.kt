package com.beletskiy.bullscows.game

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * this class implements game logic
 *
 * Properties:
 * guesses: StateFlow<List<Guess>>
 * isGameOver: StateFlow<Boolean>
 *
 * Methods:
 * restart()
 * evaluateUserInput(userInput: List<Int>): Result
 */

interface IGameController {
    val guesses: StateFlow<List<Guess>>
    val isGameOver: StateFlow<Boolean>
    fun restart()
    fun evaluateUserInput(userInput: List<Int>): Result

    companion object {
        fun getInstance() = GameControllerImpl()
    }
}

class GameControllerImpl : IGameController {
    private lateinit var secretNumber: List<Int>
    private var guessNumber = 0

    private val _guesses = MutableStateFlow(emptyList<Guess>())
    override val guesses = _guesses.asStateFlow()

    private val _isGameOver = MutableStateFlow(false)
    override val isGameOver = _isGameOver.asStateFlow()

    init {
        restart()
    }

    /**
     * Receives user input,
     * updates Guess list and game state (isGameOver),
     * returns [Result.Success] if input is valid, [Result.Failure] otherwise.
     */
    override fun evaluateUserInput(userInput: List<Int>): Result {
        if (userInput.size != 4) {
            return Result.Failure(IllegalGuessSizeException())
        }
        if (userInput.size != userInput.distinct().size) {
            return Result.Failure(RepetitiveNumbersException())
        }
        if (userInput.any { it !in 0..9 }) {
            return Result.Failure(IllegalNumberException())
        }

        val guessResult = ArrayList<GuessResult>()

        for (i in userInput.indices) {
            when {
                // if both value and position match
                userInput[i] == secretNumber[i] -> {
                    guessResult.add(GuessResult.BULL)
                }
                // if secretNumber contains this value in any position
                secretNumber.contains(userInput[i]) -> {
                    guessResult.add(GuessResult.COW)
                }
                // secretNumber doesn't contain this value
                else -> {
                    guessResult.add(GuessResult.NOTHING)
                }
            }
        }
        // BULLs first, then COWs, then NOTHINGs
        guessResult.sortDescending()
        guessNumber++
        _guesses.update { it + Guess(guessNumber, userInput, guessResult) }
        _isGameOver.update { guessResult.all { it == GuessResult.BULL } }
        return Result.Success
    }

    /**
     * restarts the game
     */
    override fun restart() {
        generateNewSecretNumber()
        guessNumber = 0
        _guesses.update { emptyList() }
        _isGameOver.update { false }
    }

    /**
     * generates new [secretNumber] - four non-repeating numbers from 0 to 9
     */
    private fun generateNewSecretNumber() {
        secretNumber = (0..9).shuffled().take(4)
        Log.d("vitDebug", "generateNewSecretNumber: $secretNumber")
    }
}
