package com.beletskiy.fifteen.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beletskiy.fifteen.data.FakeFifteenGame
import com.beletskiy.fifteen.ui.components.BoardView

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val uiState by viewModel.gameUiState.collectAsStateWithLifecycle()

    var isTheFirstLaunch by rememberSaveable { mutableStateOf(true) }
    if (isTheFirstLaunch) {
        isTheFirstLaunch = false
        LaunchedEffect(Unit) {
            viewModel.newGame()
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
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
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun FifteenGameScreenPreview() {
    val fakeGame = FakeFifteenGame()
    val previewViewModel = GameViewModel(fakeGame)
    GameScreen(viewModel = previewViewModel)
}
