package com.beletskiy.ttt.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.beletskiy.ttt.ui.screens.GameScreen
import com.beletskiy.ttt.ui.screens.GameViewModel

@Composable
fun TicTacToeGameRoute(openDrawer: () -> Unit) {
    GameScreen(
        viewModel = hiltViewModel<GameViewModel>(),
        onMenuClick = openDrawer,
    )
}
