package com.beletskiy.ttt.ui.ui

import androidx.lifecycle.ViewModel
import com.beletskiy.ttt.data.ITicTacToeGame
import com.beletskiy.ttt.data.Mark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.Int

enum class PlayerSlot(val mark: Mark) {
    PLAYER1(Mark.X),
    PLAYER2(Mark.O);

    companion object {
        fun getByMark(mark: Mark): PlayerSlot = entries.first { it.mark == mark }
    }
}

fun Mark.toPlayerSlot(): PlayerSlot = PlayerSlot.getByMark(this)

data class UiPlayer(
    val name: String,
    val slot: PlayerSlot,
    val score: Int = 0,
)

@Suppress("MagicNumber")
data class GameUiState(
    val gameSessionId: Int = 0,
    val board: List<List<Mark?>> = List(3) { List(3) { null } },
    val player1: UiPlayer = UiPlayer("Player1", PlayerSlot.PLAYER1),
    val player2: UiPlayer = UiPlayer("Player2", PlayerSlot.PLAYER2),
    val winnerSlot: PlayerSlot? = null,
    val isDraw: Boolean = false,
    val isGameOver: Boolean = false,
    val currentPlayerSlot: PlayerSlot = PlayerSlot.PLAYER1,
) {
    fun getPlayer(slot: PlayerSlot): UiPlayer =
        when (slot) {
            PlayerSlot.PLAYER1 -> player1
            PlayerSlot.PLAYER2 -> player2
        }
}

@HiltViewModel
class GameViewModel @Inject constructor(private val ticTacToeGame: ITicTacToeGame) : ViewModel() {

    private var currentMark: Mark = Mark.X
    private val _gameUiState = MutableStateFlow(GameUiState())
    val gameUiState: StateFlow<GameUiState> = _gameUiState

    fun newGame() {
        currentMark = currentMark.other()
        val gameState = ticTacToeGame.newGame(currentMark)
        _gameUiState.update {
            it.copy(
                gameSessionId = it.gameSessionId + 1,
                board = gameState.board,
                winnerSlot = gameState.winner?.toPlayerSlot(),
                isDraw = gameState.isDraw,
                isGameOver = gameState.isGameOver,
                currentPlayerSlot = PlayerSlot.getByMark(gameState.currentMark),
            )
        }
    }

    fun takeTurn(row: Int, column: Int) {
        val gameState = ticTacToeGame.makeMove(row, column)
        if (gameState == null) return // in case of invalid move or game is already over

        val currentPlayer = gameState.currentMark.toPlayerSlot()
        val winner = gameState.winner?.toPlayerSlot()

        var newGameUiState = _gameUiState.value.copy(
            board = gameState.board,
            winnerSlot = winner,
            isDraw = gameState.isDraw,
            isGameOver = gameState.isGameOver,
            currentPlayerSlot = currentPlayer,
        )
        winner?.let {
            newGameUiState = when (it) {
                PlayerSlot.PLAYER1 -> newGameUiState.copy(
                    player1 = newGameUiState.player1.copy(score = newGameUiState.player1.score + 1)
                )

                PlayerSlot.PLAYER2 -> newGameUiState.copy(
                    player2 = newGameUiState.player2.copy(score = newGameUiState.player2.score + 1)
                )
            }
        }
        _gameUiState.update {
            newGameUiState
        }
    }

    fun renamePlayers(player1Name: String, player2Name: String) {
        _gameUiState.update {
            it.copy(
                player1 = it.player1.copy(name = player1Name),
                player2 = it.player2.copy(name = player2Name),
            )
        }
    }

    fun resetScore() {
        _gameUiState.update {
            it.copy(
                player1 = it.player1.copy(score = 0),
                player2 = it.player2.copy(score = 0),
            )
        }
    }
}
