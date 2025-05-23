package com.beletskiy.reversi.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beletskiy.reversi.ui.screens.GameScreen
import com.beletskiy.reversi.ui.screens.GameViewModel

object ReversiNav {
    const val ROUTE = "reversi"
}

enum class ReversiScreen {
    Game,
}

fun NavGraphBuilder.reversiNavGraph(
    navController: NavHostController,
    onMenuClick: () -> Unit,
) {
    navigation(
        route = ReversiNav.ROUTE,
        startDestination = ReversiScreen.Game.name,
    ) {
        composable(ReversiScreen.Game.name) {
            GameScreen(
                viewModel = hiltViewModel<GameViewModel>(),
                onMenuClick = onMenuClick,
            )
        }
    }
}