@file:Suppress("MagicNumber")

package com.beletskiy.ttt.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beletskiy.ttt.data.FakeTicTacToeGame
import com.beletskiy.ttt.data.GameType
import com.beletskiy.ttt.ui.R
import com.beletskiy.ttt.ui.components.BoardView
import com.beletskiy.ttt.ui.components.EditNamesDialog
import com.beletskiy.ttt.ui.components.SegmentedButtonsDialog
import com.beletskiy.ttt.ui.components.TicTacToeAppBar
import com.beletskiy.ttt.ui.components.TwoButtonsDialog

@Suppress("detekt:LongMethod")
@Composable
fun GameScreen(viewModel: GameViewModel) {
    val uiState by viewModel.gameUiState.collectAsStateWithLifecycle()
    var showEditNamesDialog by remember { mutableStateOf(false) }
    var showResetScoreDialog by remember { mutableStateOf(false) }
    var showSelectGameTypeDialog by remember { mutableStateOf(false) }

    if (showEditNamesDialog) {
        EditNamesDialog(
            player1Name = uiState.player1.name,
            player2Name = uiState.player2.name,
            onDismissRequest = { showEditNamesDialog = false },
            onConfirmation = { name1, name2 ->
                showEditNamesDialog = false
                viewModel.renamePlayers(name1.trim(), name2.trim())
            }
        )
    }

    if (showResetScoreDialog) {
        TwoButtonsDialog(
            dialogTitle = stringResource(R.string.reset_score),
            dialogText = stringResource(R.string.are_you_sure_you_want_to_reset_the_score),
            confirmText = stringResource(R.string.confirm),
            dismissText = stringResource(R.string.dismiss),
            onDismissRequest = { showResetScoreDialog = false },
            onConfirmation = {
                showResetScoreDialog = false
                viewModel.resetScore()
            },
        )
    }

    if (showSelectGameTypeDialog) {
        SegmentedButtonsDialog(
            title = stringResource(R.string.select_game_type),
            labels = GameType.entries.map { it.name },
            initialIndex = viewModel.gameType.ordinal,
            onDismissRequest = { showSelectGameTypeDialog = false },
            onConfirmation = { index ->
                showSelectGameTypeDialog = false
                viewModel.setGameType(index)
            }
        )
    }

    Scaffold(
        topBar = {
            TicTacToeAppBar(
                title = stringResource(R.string.game_title),
                onResetScoreClicked = {
                    if (uiState.player1.score > 0 || uiState.player2.score > 0) {
                        showResetScoreDialog = true
                    }
                },
                onEditNamesClicked = {
                    showEditNamesDialog = true
                },
                onSelectGameTypeClicked = {
                    showSelectGameTypeDialog = true
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            PlayerScoreView(
                player1 = uiState.player1,
                player2 = uiState.player2,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            )

            GameStateView(
                uiState = uiState,
            ) {
                viewModel.newGame()
            }

            Spacer(modifier = Modifier.height(16.dp))

            /* We use key() {} because, for a new game, we need a completely new Board
             * with a new animation. Otherwise, the animation will be equal 1f and won't work. */
            key(uiState.gameSessionId) {
                BoardView(
                    board = uiState.board,
                    isGameOver = uiState.isGameOver,
                    modifier = Modifier.padding(8.dp),
                    positionToBeRemoved = uiState.positionToBeRemoved,
                ) { row, column ->
                    viewModel.takeTurn(row, column)
                }
            }
        }
    }
}

@Composable
fun GameStateView(
    uiState: GameUiState,
    modifier: Modifier = Modifier,
    onNewGameClick: () -> Unit = {},
) {
    val isGameOver = uiState.isGameOver
    val winner = uiState.winnerSlot?.let { uiState.getPlayer(it) }
    val isDraw = uiState.isDraw
    val currentPlayer = uiState.getPlayer(uiState.currentPlayerSlot)
    val currentMark = when (currentPlayer.slot) {
        PlayerSlot.PLAYER1 -> "X"
        PlayerSlot.PLAYER2 -> "O"
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val text = when {
            isGameOver && isDraw -> stringResource(R.string.it_s_a_draw)
            isGameOver && winner != null -> stringResource(R.string.player_won, winner.name)
            else -> stringResource(R.string.players_turn, currentPlayer.name, currentMark)
        }
        Text(
            text = text,
            modifier = Modifier.padding(end = 8.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        val iconModifier = if (isGameOver) Modifier else Modifier.width(0.dp)
        IconButton(
            onClick = onNewGameClick,
            modifier = iconModifier,
        ) {
            Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.new_game))
        }
    }
}

@Composable
private fun PlayerScoreView(
    player1: UiPlayer,
    player2: UiPlayer,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = player1.name,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "${player1.score} : ${player2.score}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = player2.name,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Start,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun PreviewGameScreen() {
    val fakeGame = FakeTicTacToeGame()
    val previewViewModel = GameViewModel(fakeGame)
    GameScreen(viewModel = previewViewModel)
}
