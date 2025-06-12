package com.beletskiy.reversi.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beletskiy.reversi.data.FakeReversiGame
import com.beletskiy.reversi.ui.components.ReversiAppBar
import com.beletskiy.shared.theme.GamesTheme
import com.beletskiy.reversi.ui.R
import com.beletskiy.reversi.ui.components.BoardView
import com.beletskiy.reversi.ui.components.ScoreBoardView
import com.beletskiy.shared.components.StartNewGameView
import com.beletskiy.shared.components.TwoButtonsDialog

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onMenuClick: () -> Unit = {},
) {
    val uiState by viewModel.gameUiState.collectAsStateWithLifecycle()
    var showRestartGameDialog by remember { mutableStateOf(false) }

    if (showRestartGameDialog) {
        TwoButtonsDialog(
            title = stringResource(R.string.restart_game),
            message = stringResource(R.string.are_you_sure_you_want_to_restart_the_game),
            confirmText = stringResource(R.string.confirm),
            dismissText = stringResource(R.string.dismiss),
            onDismissRequest = { showRestartGameDialog = false },
            onConfirmation = {
                showRestartGameDialog = false
                viewModel.newGame()
            },
        )
    }

    var isTheFirstLaunch by rememberSaveable { mutableStateOf(true) }
    if (isTheFirstLaunch) {
        isTheFirstLaunch = false
        LaunchedEffect(Unit) {
            viewModel.newGame()
        }
    }

    Scaffold(
        topBar = {
            ReversiAppBar(
                title = stringResource(R.string.reversi_game_title),
                onMenu = onMenuClick,
                onRestartGame = {
                    showRestartGameDialog = true
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                ScoreBoardView(
                    blackScore = uiState.blackScore,
                    whiteScore = uiState.whiteScore,
                    currentDisc = uiState.currentPlayerDisc,
                    isGameOver = uiState.isGameOver,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                        .padding(8.dp),
                )
            }

            key(uiState.gameSessionId) {
                BoardView(
                    board = uiState.board,
                    isGameOver = uiState.isGameOver,
                    currentDisc = uiState.currentPlayerDisc,
                    modifier = Modifier
                        .padding(8.dp),
                ) { row, column ->
                    viewModel.takeTurn(row, column)
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                if (uiState.isGameOver) {
                    StartNewGameView(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center),
                    ) {
                        viewModel.newGame()
                    }
                }
            }
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(widthDp = 360, heightDp = 722)
@Composable
private fun PreviewGameScreen() {
    GamesTheme {
        val fakeGame = FakeReversiGame()
        val previewViewModel = GameViewModel(fakeGame)
        GameScreen(viewModel = previewViewModel)
    }
}
