package com.beletskiy.bullscows.game

import com.beletskiy.bullscows.utils.DuplicateNumbersException

/**
 * this class implements game logic
 */
class GameController {
    private lateinit var secretNumber: List<Int>
    private var guessNumber = 0

    init {
        generateNewSecretNumber()
    }

    /**
     * gets user input - some number of Int
     * returns:
     * - list of these Int if they are four and non-repeating
     * - throw IllegalArgumentException if they are not four
     * - throw custom RepeatingNumbersException it there are repeating Int
     */
    fun isUserInputValid(vararg args: Int): List<Int> {
        require(4 == args.size) { "There must be four Int." }
        if (args.toSet().size != args.size) {
            throw DuplicateNumbersException("There are repeating numbers.")
        }
        return args.toList()
    }

    /**
     * gets 4 numbers from user input, evaluate them and return [Guess] with results
     */
    fun evaluateUserInput(userInput: List<Int>): Guess {
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
        return Guess(guessNumber, userInput, result)
    }

    /**
     * resets the game
     */
    fun reset() {
        generateNewSecretNumber()
        guessNumber = 0
    }

    /**
     * checks if the game is over = result is all BULLs
     */
    fun isGameOver(guess: Guess) = guess.results.all { it == Result.BULL }

    /**
     * generates new [secretNumber] - four non-repeating numbers from 0 to 9
     */
    private fun generateNewSecretNumber() {
        secretNumber = (0..9).shuffled().take(4)
    }
}
