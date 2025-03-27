package com.beletskiy.bac.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.beletskiy.bac.ui.ui.screens.RulesScreen
import com.beletskiy.bac.ui.ui.screens.GameScreen
import com.beletskiy.bac.ui.ui.screens.GameViewModel

object BullsAndCowsNav {
    const val ROUTE = "bulls_and_cows"
}

enum class BullsAndCowsScreen {
    Game,
    Rules,
}


fun NavGraphBuilder.bullsAndCowsNavGraph(navController: NavHostController) {
    navigation(
        route = BullsAndCowsNav.ROUTE,
        startDestination = BullsAndCowsScreen.Game.name
    ) {
        composable(BullsAndCowsScreen.Game.name) {
            GameScreen(
                viewModel = hiltViewModel<GameViewModel>(),
                onNavigateUp = { navController.navigateUp() },
                onNavigateToRules = { navController.navigate(BullsAndCowsScreen.Rules.name) },
            )
        }
        composable(BullsAndCowsScreen.Rules.name) {
            RulesScreen(onNavigateUp = { navController.navigateUp() })
        }
    }
}
