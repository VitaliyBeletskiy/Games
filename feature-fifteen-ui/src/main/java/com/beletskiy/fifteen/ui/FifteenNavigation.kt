package com.beletskiy.fifteen.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

object FifteenNav {
    const val ROUTE = "fifteen"
}

enum class FifteenScreen {
    FifteenGameScreen,
}

fun NavGraphBuilder.fifteenNavGraph(navController: NavHostController) {
    navigation(
        route = FifteenNav.ROUTE,
        startDestination = FifteenScreen.FifteenGameScreen.name,
    ) {
        composable(FifteenScreen.FifteenGameScreen.name) {
            FifteenGameScreen()
        }
    }
}
