package com.beletskiy.ttt.ui.ui

import androidx.lifecycle.ViewModel
import com.beletskiy.ttt.data.ITicTacToeGame
import com.beletskiy.ttt.data.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class GameUiState(
    val board: List<List<Player?>> = List(3) { List(3) { null } },
    val isGameOver: Boolean = false,
    val currentPlayer: Int = 1,
)

@HiltViewModel
class GameViewModel @Inject constructor(private val ticTacToeGame: ITicTacToeGame) : ViewModel() {

    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState> = _gameUiState

    fun newGame(player: Player? = null) {
        ticTacToeGame.newGame(player)
    }

    fun takeTurn(row: Int, column: Int) {
        val gameState = ticTacToeGame.makeMove(row, column)
        if (gameState == null) return

        _gameUiState.update {
            it.copy(
                board = gameState.board,
            )
        }
    }
}
