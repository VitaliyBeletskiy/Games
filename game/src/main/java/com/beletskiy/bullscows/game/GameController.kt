package com.beletskiy.bullscows.game

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This class implements game logic.
 *
 * @property guesses The state flow of the list of guesses made in the game.
 */
interface IGameController {
    val guesses: StateFlow<List<Guess>>

    /**
     * Restarts the game.
     */
    fun restart()

    /**
     * Evaluates the user input and updates the game state.
     *
     * @param userInput The user input, which should be a list of four unique digits.
     * @return True if the game is over (i.e., the user has guessed the secret number), false otherwise.
     */
    fun evaluateUserInput(userInput: List<Int>): Boolean

    companion object {
        fun getInstance() = GameControllerImpl()
    }
}

/**
 * Implementation of the IGameController interface.
 */
class GameControllerImpl : IGameController {
    private lateinit var secretNumber: List<Int>
    private var guessNumber = 0
    private val _guesses = MutableStateFlow(emptyList<Guess>())
    override val guesses: StateFlow<List<Guess>> = _guesses.asStateFlow()

    init {
        generateNewSecretNumber()
    }

    /**
     * Evaluates the user input and updates the game state.
     *
     * This method ensures that the user input is valid (four unique digits)
     * and then compares it to the secret number, generating a list of results
     * (BULL, COW, NOTHING) for each digit. The results are then sorted and
     * added to the list of guesses.
     *
     * @param userInput The user input, which should be a list of four unique digits.
     * @return True if the game is over (i.e., the user has guessed the secret number), false otherwise.
     */
    override fun evaluateUserInput(userInput: List<Int>): Boolean {
        require(userInput.size == 4) { "User input must contain 4 numbers" }
        require(userInput.size == userInput.distinct().size) { "User input must contain unique numbers" }

        val result = ArrayList<Result>()

        for (i in userInput.indices) {
            when {
                // if both value and position match
                userInput[i] == secretNumber[i] -> {
                    result.add(Result.BULL)
                }
                // if secretNumber contains this value in any position
                secretNumber.contains(userInput[i]) -> {
                    result.add(Result.COW)
                }
                // secretNumber doesn't contain this value
                else -> {
                    result.add(Result.NOTHING)
                }
            }
        }

        // BULLs first, then COWs, then NOTHINGs
        result.sortDescending()
        guessNumber++
        _guesses.update { it + Guess(guessNumber, userInput, result) }
        return result.all { it == Result.BULL }
    }

    /**
     * Restarts the game by generating a new secret number, resetting the guess counter,
     * and clearing the list of guesses.
     */
    override fun restart() {
        generateNewSecretNumber()
        guessNumber = 0
        _guesses.update { emptyList() }
    }

    /**
     * Generates a new secret number, which is a list of four unique digits.
     */
    private fun generateNewSecretNumber() {
        secretNumber = (0..9).shuffled().take(4)
    }
}
