package com.beletskiy.fifteen.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beletskiy.fifteen.data.FakeFifteenGame
import com.beletskiy.fifteen.data.MoveType
import com.beletskiy.fifteen.ui.R
import com.beletskiy.fifteen.ui.components.BoardView
import com.beletskiy.fifteen.ui.components.FifteenAppBar
import com.beletskiy.shared.components.OneButtonDialog
import com.beletskiy.shared.components.SegmentedButtonsDialog
import com.beletskiy.shared.components.TwoButtonsDialog
import com.beletskiy.shared.theme.GamesTheme

@Suppress("detekt:LongMethod")
@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onMenuClick: () -> Unit = {},
) {
    val uiState by viewModel.gameUiState.collectAsStateWithLifecycle()
    var showRestartGameDialog by remember { mutableStateOf(false) }
    var showSelectMoveTypeDialog by remember { mutableStateOf(false) }
    val segmentedButtonsLabels = MoveType.entries.map {
        when (it) {
            MoveType.SINGLE_TILE -> stringResource(R.string.single_tile)
            MoveType.MULTI_TILE -> stringResource(R.string.many_tiles)
        }
    }

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

    if (showSelectMoveTypeDialog) {
        SegmentedButtonsDialog(
            title = stringResource(R.string.select_move_type),
            labels = segmentedButtonsLabels,
            initialIndex = viewModel.moveType.ordinal,
            onDismissRequest = { showSelectMoveTypeDialog = false },
            onConfirm = {
                showSelectMoveTypeDialog = false
                viewModel.setMoveType(it)
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
            FifteenAppBar(
                title = stringResource(R.string.fifteen_game_title),
                onMenu = onMenuClick,
                onRestartGame = {
                    showRestartGameDialog = true
                },
                onSelectMoveType = {
                    showSelectMoveTypeDialog = true
                },
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                key(uiState.gameSessionId) {
                    BoardView(
                        board = uiState.board,
                        isGameOver = uiState.isGameOver,
                        modifier = Modifier.padding(8.dp),
                    ) { row, column ->
                        viewModel.takeTurn(row, column)
                    }
                }
            }

            if (uiState.isGameOver) {
                OneButtonDialog(
                    text = stringResource(R.string.puzzle_solved),
                    buttonText = stringResource(R.string.play_again),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    onPlayAgain = {
                        viewModel.newGame()
                    },
                )
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun FifteenGameScreenPreview() {
    GamesTheme {
        val fakeGame = FakeFifteenGame()
        val previewViewModel = GameViewModel(fakeGame)
        GameScreen(viewModel = previewViewModel)
    }
}
