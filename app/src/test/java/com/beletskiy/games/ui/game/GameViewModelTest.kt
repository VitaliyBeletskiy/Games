package com.beletskiy.bullscows.games.ui.game

import app.cash.turbine.test
import com.beletskiy.bullscows.game.GameControllerImpl
import com.beletskiy.bullscows.game.IGameController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var gameController: IGameController
    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        gameController = GameControllerImpl()
        viewModel = GameViewModel(gameController)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun gameViewModel_Initialization_InitialDataLoaded() = runTest {
        viewModel.gameUiState.test {
            assertEquals(
                GameUiState(
                    guesses = emptyList(),
                    isGameOver = false,
                    message = null,
                ),
                awaitItem()
            )
        }
        assertTrue(viewModel.canRestartSilently)
    }

    @Test
    fun gameViewModel_UserInput_UiStateIsUpdated() = runTest {
        viewModel.gameUiState.test {
            // Collect Initial state
            awaitItem()

            viewModel.evaluateUserInput(listOf(1, 2, 3, 4))
            val updatedUiState = awaitItem()
            assertTrue(updatedUiState.guesses.size == 1)
            assertTrue(updatedUiState.guesses[0].userInput == listOf(1, 2, 3, 4))

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun gameViewModel_RestartGame_UiStateIsBackToInitialState() = runTest {
        viewModel.gameUiState.test {
            // Collect Initial state
            awaitItem()

            viewModel.evaluateUserInput(listOf(1, 2, 3, 4))
            // Collect first emit from GameController
            awaitItem()

            viewModel.restart()

            val uiState = awaitItem()
            assertTrue(uiState.guesses.isEmpty())
            assertTrue(!uiState.isGameOver)
            assertTrue(uiState.message == null)
        }
    }

    @Test
    fun gameViewModel_UserEntersRepetitiveNumbers_MessageIsNotNullInUiState() = runTest {
        viewModel.gameUiState.test {
            // Collect Initial state
            awaitItem()

            viewModel.evaluateUserInput(listOf(1, 2, 2, 4))

            val uiState = awaitItem()
            assertTrue(uiState.guesses.isEmpty())
            assertTrue(!uiState.isGameOver)
            assertTrue(uiState.message != null)
        }
    }

    @Test
    fun gameViewModel_ResetMessage_MessageIsBackToNullInUiState() = runTest {
        viewModel.gameUiState.test {
            // Collect Initial state
            awaitItem()

            viewModel.evaluateUserInput(listOf(1, 2, 2, 4))
            awaitItem()

            viewModel.onMessageShown()

            val uiState = awaitItem()
            assertTrue(uiState.guesses.isEmpty())
            assertTrue(!uiState.isGameOver)
            assertTrue(uiState.message == null)
        }
    }
}
