package com.beletskiy.bullscows.compose.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beletskiy.bullscows.game.Guess
import com.beletskiy.bullscows.game.IGameController
import com.beletskiy.bullscows.game.IllegalGuessSizeException
import com.beletskiy.bullscows.game.IllegalNumberException
import com.beletskiy.bullscows.game.RepetitiveNumbersException
import com.beletskiy.bullscows.game.ifFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class Message {
    MUST_CONTAIN_FOUR_NUMBERS_WARNING,
    MUST_CONTAIN_UNIQUE_NUMBERS_WARNING,
    MUST_CONTAIN_NUMBERS_0_9_WARNING,
}

data class GameUiState(
    val guesses: List<Guess> = emptyList(),
    val isGameOver: Boolean = false,
    val message: Message? = null,
)

@Suppress("ktlint:standard:annotation")
@HiltViewModel
class GameViewModel @Inject constructor(private val gameController: IGameController) : ViewModel() {

    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState>
        get() = _gameUiState
    val canRestartSilently: Boolean
        get() = _gameUiState.value.guesses.isEmpty() || _gameUiState.value.isGameOver

    // Doesn't look neat, but I wanted to have a UI state class
    init {
        viewModelScope.launch {
            gameController.guesses.collect { guesses ->
                _gameUiState.update { it.copy(guesses = guesses.toList()) }
            }
        }
        viewModelScope.launch {
            gameController.isGameOver.collect { isOver ->
                _gameUiState.update { it.copy(isGameOver = isOver) }
            }
        }
    }

    fun restart() {
        gameController.restart()
    }

    fun evaluateUserInput(userInput: List<Int>) {
        gameController.evaluateUserInput(userInput).ifFailure { exception ->
            when (exception) {
                is IllegalGuessSizeException -> {
                    showMessage(Message.MUST_CONTAIN_FOUR_NUMBERS_WARNING)
                }

                is RepetitiveNumbersException -> {
                    showMessage(Message.MUST_CONTAIN_UNIQUE_NUMBERS_WARNING)
                }

                is IllegalNumberException -> {
                    showMessage(Message.MUST_CONTAIN_NUMBERS_0_9_WARNING)
                }
            }
        }
    }

    fun onMessageShown() {
        _gameUiState.update { it.copy(message = null) }
    }

    private fun showMessage(message: Message) {
        _gameUiState.update { it.copy(message = message) }
    }
}
