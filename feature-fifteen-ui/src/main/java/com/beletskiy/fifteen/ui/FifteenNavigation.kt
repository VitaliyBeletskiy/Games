package com.beletskiy.fifteen.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.beletskiy.fifteen.ui.screens.GameScreen
import com.beletskiy.fifteen.ui.screens.GameViewModel

@Composable
fun FifteenGameRoute(openDrawer: () -> Unit) {
    GameScreen(
        viewModel = hiltViewModel<GameViewModel>(),
        onMenuClick = openDrawer,
    )
}
