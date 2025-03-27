package com.beletskiy.bullsandcows.game

import com.beletskiy.bullscows.game.GameControllerImpl
import com.beletskiy.bullscows.game.GuessResult
import com.beletskiy.bullscows.game.MAX_NUMBER
import com.beletskiy.bullscows.game.MIN_NUMBER
import com.beletskiy.bullscows.game.Result
import com.beletskiy.bullscows.game.SECRET_NUMBER_SIZE
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GameControllerTest {
    private val gameController = GameControllerImpl()

    @Test
    fun gameController_Initialization_NewSecretGeneratedAndGameOverFalse() {
        // Assert that the secretNumber has SECRET_NUMBER_SIZE digits and is not empty.
        assertTrue(gameController.secretNumber.size == SECRET_NUMBER_SIZE)
        // Assert that the secretNumber has no repetitive digits.
        assertTrue(gameController.secretNumber.distinct().size == SECRET_NUMBER_SIZE)
        // Assert that the secretNumber has numbers only from MIN_NUMBER..MAX_NUMBER
        assertTrue(gameController.secretNumber.all { it in MIN_NUMBER..MAX_NUMBER })

        // Assert that the game is not over.
        assertTrue(!gameController.isGameOver.value)

        // Assert that the guesses list is empty.
        assertTrue(gameController.guesses.value.isEmpty())

        // Assert that the guessNumber is 0.
        assertTrue(gameController.guessNumber == 0)
    }

    @Test
    fun gameController_WrongGuess_GameOverFalseAndGuessNumberIncrementedAndGuessAddedToList() {
        val wrongUserInput = gameController.secretNumber.reversed()
        gameController.evaluateUserInput(wrongUserInput)

        // Assert that the game is not over.
        assertTrue(!gameController.isGameOver.value)
        // Assert that the guessNumber is incremented by 1.
        assertTrue(gameController.guessNumber == 1)
        // Assert that the guesses list has 1 item.
        assertTrue(gameController.guesses.value.size == 1)
        // Assert that the guess added to the list is correct.
        assertTrue(gameController.guesses.value[0].userInput == wrongUserInput)
    }

    @Test
    fun gameController_CorrectGuess_GameOverTrueAndGuessNumberIncrementedAndGuessAddedToList() {
        val correctUserInput = gameController.secretNumber
        gameController.evaluateUserInput(correctUserInput)

        // Assert that the game is over.
        assertTrue(gameController.isGameOver.value)
        // Assert that the guessNumber is incremented by 1.
        assertTrue(gameController.guessNumber == 1)
        // Assert that the guesses list has 1 item.
        assertTrue(gameController.guesses.value.size == 1)
        // Assert that the guess added to the list is correct.
        assertTrue(gameController.guesses.value[0].userInput == correctUserInput)
    }

    @Test
    fun gameController_Restart_NewSecretGeneratedAndGameOverFalse() {
        val wrongUserInput = gameController.secretNumber.reversed()
        gameController.evaluateUserInput(wrongUserInput)

        gameController.restart()
        // Assert that the secretNumber has SECRET_NUMBER_SIZE digits and is not empty.
        assertTrue(gameController.secretNumber.size == SECRET_NUMBER_SIZE)
        // Assert that the secretNumber has no repetitive digits.
        assertTrue(gameController.secretNumber.distinct().size == SECRET_NUMBER_SIZE)
        // Assert that the secretNumber has numbers only from MIN_NUMBER..MAX_NUMBER
        assertTrue(gameController.secretNumber.all { it in MIN_NUMBER..MAX_NUMBER })
        // Assert that the game is not over.
        assertTrue(!gameController.isGameOver.value)
        // Assert that the guesses list is empty.
        assertTrue(gameController.guesses.value.isEmpty())
        // Assert that the guessNumber is 0.
        assertTrue(gameController.guessNumber == 0)
    }

    @Test
    fun gameController_GuessWithRepetitiveNumbers_Failure() {
        val wrongUserInput = List(SECRET_NUMBER_SIZE) { MIN_NUMBER }
        val result = gameController.evaluateUserInput(wrongUserInput)
        assertTrue(result is Result.Failure)
    }

    @Test(expected = IllegalArgumentException::class)
    fun gameController_GuessWithWrongLength_Exception() {
        val illegalUserInput = (0..SECRET_NUMBER_SIZE).toList().map { MIN_NUMBER + it }
        gameController.evaluateUserInput(illegalUserInput)
    }

    @Test(expected = IllegalArgumentException::class)
    fun gameController_GuessWithIllegalNumbers_Exception() {
        val illegalUserInput = (0..<SECRET_NUMBER_SIZE).toList().map { MAX_NUMBER + it }
        gameController.evaluateUserInput(illegalUserInput)
    }

    @Test
    fun gameController_GuessWithOneBull_Success() {
        val userInput = mutableListOf(gameController.secretNumber[0])

        for (i in MIN_NUMBER..MAX_NUMBER) {
            if (!gameController.secretNumber.contains(i)) {
                userInput.add(i)
            }
            if (userInput.size == SECRET_NUMBER_SIZE) {
                break
            }
        }
        gameController.evaluateUserInput(userInput)

        assertTrue(
            gameController.guesses.value[0].guessResults == listOf(
                GuessResult.BULL,
                GuessResult.NOTHING,
                GuessResult.NOTHING,
                GuessResult.NOTHING
            )
        )

        val expectedGuessResult =
            listOf(GuessResult.BULL) + List(SECRET_NUMBER_SIZE - 2) { GuessResult.NOTHING }
        assertFalse(gameController.guesses.value[0].guessResults == expectedGuessResult)
    }

    @Test
    fun gameController_GuessWithOneCow_Success() {
        val userInput = mutableListOf(gameController.secretNumber[1])

        for (i in MIN_NUMBER..MAX_NUMBER) {
            if (!gameController.secretNumber.contains(i)) {
                userInput.add(i)
            }
            if (userInput.size == SECRET_NUMBER_SIZE) {
                break
            }
        }
        gameController.evaluateUserInput(userInput)

        val expectedGuessResult =
            listOf(GuessResult.COW) + List(SECRET_NUMBER_SIZE - 2) { GuessResult.NOTHING }
        assertFalse(gameController.guesses.value[0].guessResults == expectedGuessResult)
    }

    @Test
    fun gameController_GuessWithAllNothing_Success() {
        val userInput = mutableListOf<Int>()

        for (i in MIN_NUMBER..MAX_NUMBER) {
            if (!gameController.secretNumber.contains(i)) {
                userInput.add(i)
            }
            if (userInput.size == SECRET_NUMBER_SIZE) {
                break
            }
        }
        gameController.evaluateUserInput(userInput)

        val expectedGuessResult = List(SECRET_NUMBER_SIZE - 1) { GuessResult.NOTHING }
        assertFalse(gameController.guesses.value[0].guessResults == expectedGuessResult)
    }
}
