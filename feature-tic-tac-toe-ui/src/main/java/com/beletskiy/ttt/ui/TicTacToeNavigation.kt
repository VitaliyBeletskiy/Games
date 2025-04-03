package com.beletskiy.ttt.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beletskiy.ttt.ui.ui.GameScreen
import com.beletskiy.ttt.ui.ui.GameViewModel

object TicTacToeNav {
    const val ROUTE = "tic_tac_toe"
}

enum class TicTacToeScreen {
    Game,
}

fun NavGraphBuilder.ticTacToeNavGraph(navController: NavHostController) {
    navigation(
        route = TicTacToeNav.ROUTE,
        startDestination = TicTacToeScreen.Game.name,
    ) {
        composable(TicTacToeScreen.Game.name) {
            GameScreen(
                viewModel = hiltViewModel<GameViewModel>(),
            )
        }
    }
}
