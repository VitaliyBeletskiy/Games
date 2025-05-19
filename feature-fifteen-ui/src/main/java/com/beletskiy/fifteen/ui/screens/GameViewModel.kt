package com.beletskiy.fifteen.ui.screens

import androidx.lifecycle.ViewModel
import com.beletskiy.fifteen.data.IFifteenGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class GameUiState(
    val gameSessionId: Int = 0,
    val board: List<List<Int>> = List(IFifteenGame.BOARD_SIZE) { List(IFifteenGame.BOARD_SIZE) { 0 } },
    val isGameOver: Boolean = false,
)

@HiltViewModel
class GameViewModel @Inject constructor(private val fifteenGame: IFifteenGame) : ViewModel() {

    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState> = _gameUiState

    fun newGame() {
        val gameStatus = fifteenGame.newGame()
        _gameUiState.update {
            it.copy(
                gameSessionId = it.gameSessionId + 1,
                board = gameStatus.board,
                isGameOver = gameStatus.isGameOver,
            )
        }
    }

    fun takeTurn(row: Int, col: Int) {
        val gameStatus = fifteenGame.makeMove(row, col)
        _gameUiState.update {
            it.copy(
                board = gameStatus.board,
                isGameOver = gameStatus.isGameOver,
            )
        }
    }
}
