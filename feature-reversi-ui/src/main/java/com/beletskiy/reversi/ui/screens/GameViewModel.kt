package com.beletskiy.reversi.ui.screens

import androidx.lifecycle.ViewModel
import com.beletskiy.reversi.data.Cell
import com.beletskiy.reversi.data.IReversiGame
import com.beletskiy.reversi.data.PlayerDisc
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class GameUiState(
    val gameSessionId: Int = 0,
    val board: List<List<Cell>> = List(IReversiGame.BOARD_SIZE) { List(IReversiGame.BOARD_SIZE) { Cell() } },
    val currentPlayerDisc: PlayerDisc = PlayerDisc.BLACK,
    val blackScore: Int = 0,
    val whiteScore: Int = 0,
    val isGameOver: Boolean = false,
)

@HiltViewModel
class GameViewModel @Inject constructor(private val reversiGame: IReversiGame) : ViewModel() {

    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState> = _gameUiState

    fun newGame() {
        val gameState = reversiGame.newGame()
        _gameUiState.update {
            it.copy(
                gameSessionId = it.gameSessionId + 1,
                board = gameState.board,
                currentPlayerDisc = gameState.currentPlayer,
                blackScore = gameState.blackScore,
                whiteScore = gameState.whiteScore,
                isGameOver = gameState.isGameOver,
            )
        }
    }

    fun takeTurn(row: Int, col: Int) {
        val gameState = reversiGame.makeMove(row, col)
        _gameUiState.update {
            it.copy(
                board = gameState.board,
                currentPlayerDisc = gameState.currentPlayer,
                blackScore = gameState.blackScore,
                whiteScore = gameState.whiteScore,
                isGameOver = gameState.isGameOver,
            )
        }
    }
}
