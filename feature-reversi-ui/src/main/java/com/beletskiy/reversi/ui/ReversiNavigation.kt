package com.beletskiy.reversi.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.beletskiy.reversi.ui.screens.GameScreen
import com.beletskiy.reversi.ui.screens.GameViewModel

@Composable
fun ReversiGameRoute(openDrawer: () -> Unit) {
    GameScreen(
        viewModel = hiltViewModel<GameViewModel>(),
        onMenuClick = openDrawer,
    )
}
