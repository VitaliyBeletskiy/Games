package com.beletskiy.ttt.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

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
            GameScreen()
        }
    }
}
