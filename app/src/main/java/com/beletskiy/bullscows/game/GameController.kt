package com.beletskiy.bullscows.game

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * this class implements game logic
 *
 * Properties:
 * isGameOver: Flow<Boolean>
 *
 * Methods:
 * restart()
 * evaluateUserInput(userInput: List<Int>)
 */
class GameController {
    private lateinit var secretNumber: List<Int>
    private var guessNumber = 0

    private val _guesses = MutableStateFlow(emptyList<Guess>())
    val guesses = _guesses.asStateFlow()

    init {
        generateNewSecretNumber()
    }

    /**
     * Receives VALID user input (four non-repeating numbers from 0 to 9),
     * updates Guess list,
     * returns true if game is over, false otherwise.
     */
    fun evaluateUserInput(userInput: List<Int>): Boolean {
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
     * restarts the game
     */
    fun restart() {
        generateNewSecretNumber()
        guessNumber = 0
        _guesses.update { emptyList() }
    }

    /**
     * generates new [secretNumber] - four non-repeating numbers from 0 to 9
     */
    private fun generateNewSecretNumber() {
        secretNumber = (0..9).shuffled().take(4)
    }
}
