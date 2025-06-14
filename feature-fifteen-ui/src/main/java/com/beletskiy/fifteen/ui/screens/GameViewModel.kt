package com.beletskiy.fifteen.ui.screens

import androidx.lifecycle.ViewModel
import com.beletskiy.fifteen.data.IFifteenGame
import com.beletskiy.fifteen.data.MoveType
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

    var moveType = MoveType.SINGLE_TILE
        private set
    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState> = _gameUiState

    fun newGame() {
        val gameState = fifteenGame.newGame()
        _gameUiState.update {
            it.copy(
                gameSessionId = it.gameSessionId + 1,
                board = gameState.board,
                isGameOver = gameState.isGameOver,
            )
        }
    }

    fun takeTurn(row: Int, col: Int) {
        val gameState = fifteenGame.makeMove(row, col, moveType)
        _gameUiState.update {
            it.copy(
                board = gameState.board,
                isGameOver = gameState.isGameOver,
            )
        }
    }

    fun setMoveType(index: Int) {
        moveType = MoveType.entries[index]
    }
}
