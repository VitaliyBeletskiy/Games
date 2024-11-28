package com.beletskiy.bullscows.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.beletskiy.bullscows.compose.ui.game.GameScreen
import com.beletskiy.bullscows.compose.ui.game.GameViewModel
import com.beletskiy.bullscows.compose.ui.rules.RulesScreen

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
            GameScreen(navController = navController, viewModel = hiltViewModel<GameViewModel>())
        }

        composable(AppScreens.RulesScreen.name) {
            RulesScreen(navController = navController)
        }
    }
}
