package com.beletskiy.fifteen.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beletskiy.fifteen.ui.screens.GameScreen
import com.beletskiy.fifteen.ui.screens.GameViewModel

object FifteenNav {
    const val ROUTE = "fifteen"
}

enum class FifteenScreen {
    FifteenGameScreen,
}

fun NavGraphBuilder.fifteenNavGraph(
    navController: NavHostController,
    onMenuClick: () -> Unit,
) {
    navigation(
        route = FifteenNav.ROUTE,
        startDestination = FifteenScreen.FifteenGameScreen.name,
    ) {
        composable(FifteenScreen.FifteenGameScreen.name) {
            GameScreen(
                viewModel = hiltViewModel<GameViewModel>(),
                onMenuClick = onMenuClick,
            )
        }
    }
}
