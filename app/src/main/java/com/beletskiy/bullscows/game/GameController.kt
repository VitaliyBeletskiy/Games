package com.beletskiy.bullscows.game

import android.util.Log
import com.beletskiy.bullscows.TAG

/**
 * this class implements game logic
 */
class GameController {
    private lateinit var secretNumber: List<Int>
    private var attemptNumber = 0

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
        if (4 != args.size) {
            throw IllegalArgumentException("There must be four Int.")
        }
        val numbersSorted = args.sorted()
        for (i in 0..args.size - 2) {
            if (numbersSorted[i] == numbersSorted[i + 1]) {
                throw com.beletskiy.bullscows.utils.RepeatingNumbersException("There are repeating numbers.")
            }
        }
        return args.toList()
    }

    /**
     * gets 4 numbers from user input, evaluate them and return [Attempt] with results
     */
    fun evaluateAttempt(attemptValues: List<Int>): Attempt {
        // TODO: 22/02/2021 check that we got exactly 4 non-repeating numbers from 0..9 as a parameter

        val result = ArrayList<Attempt.Result>()

        for (i in attemptValues.indices) {
            when {
                // if both value and position match
                attemptValues[i] == secretNumber[i] -> {
                    result.add(Attempt.Result.BULL)
                }
                // if secretNumber contains this value in any position
                secretNumber.contains(attemptValues[i]) -> {
                    result.add(Attempt.Result.COW)
                }
                // secretNumber doesn't contain this value
                else -> {
                    result.add(Attempt.Result.NOTHING)
                }
            }
        }
        // BULLs first, then COWs, then NOTHINGs
        result.sortDescending()

        attemptNumber++
        return Attempt(attemptNumber, attemptValues, result)
    }

    /**
     * resets the game
     */
    fun reset() {
        generateNewSecretNumber()
        attemptNumber = 0
    }

    /**
     * checks if the game is over = result is all BULLs
     */
    fun isGameOver(attempt: Attempt): Boolean {
        for (item in attempt.attemptResults) {
            if (item != Attempt.Result.BULL) return false
        }
        return true
    }

    /**
     * generates new [secretNumber] - four non-repeating numbers from 0 to 9
     */
    private fun generateNewSecretNumber() {
        secretNumber = (0..9).shuffled().take(4)
        Log.i(TAG, "Secret number = ${secretNumber}")
    }
}
