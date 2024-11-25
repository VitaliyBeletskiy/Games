package com.beletskiy.bullscows.game

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This class implements game logic.
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

internal const val MIN_NUMBER = 0
internal const val MAX_NUMBER = 9
internal const val SECRET_NUMBER_SIZE = 4

class GameControllerImpl : IGameController {

    private val allowedRange = MIN_NUMBER..MAX_NUMBER

    internal lateinit var secretNumber: List<Int>
    internal var guessNumber = 0

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
        //region Fail fast validations
        require(
            userInput.size == SECRET_NUMBER_SIZE,
        ) { "A guess must contain 4 numbers" }
        require(
            userInput.all { it in allowedRange },
        ) { "A guess must contain numbers from 0 to 9" }
        //endregion

        if (userInput.size != userInput.distinct().size) {
            return Result.Failure(RepetitiveNumbersException())
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
     * Restarts the game by generating a new secret number, resetting the guess counter,
     * and clearing the list of guesses.
     */
    override fun restart() {
        generateNewSecretNumber()
        guessNumber = 0
        _guesses.update { emptyList() }
        _isGameOver.update { false }
    }

    /**
     * Generates a new secret number, which is a list of four unique digits.
     */
    private fun generateNewSecretNumber() {
        secretNumber = (allowedRange).shuffled().take(SECRET_NUMBER_SIZE)
    }
}
