package com.beletskiy.games

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.beletskiy.games.ui.game.GameScreen
import com.beletskiy.games.ui.game.GameViewModel
import com.beletskiy.games.ui.rules.RulesScreen

enum class AppScreens {
    GameScreen,
    RulesScreen,
    ;

    companion object {
        fun fromRoute(route: String?): AppScreens = when (route?.substringBefore("/")) {
            GameScreen.name -> GameScreen
            RulesScreen.name -> RulesScreen
            null -> GameScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = AppScreens.GameScreen.name) {
        composable(AppScreens.GameScreen.name) {
            GameScreen(
                viewModel = hiltViewModel<GameViewModel>(),
                onNavigateUp = { navController.navigateUp() },
                onNavigateToRules = { navController.navigate(AppScreens.RulesScreen.name) },
            )
        }

        composable(AppScreens.RulesScreen.name) {
            RulesScreen(onNavigateUp = { navController.navigateUp() })
        }
    }
}
