package com.beletskiy.bullscows.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.beletskiy.bullscows.compose.ui.game.GameScreen
import com.beletskiy.bullscows.compose.ui.rules.RulesScreen

interface BullsCowsDestination {
    val route: String
}

object GameScreenDestination : BullsCowsDestination {
    override val route = "gameScreen"
}

object RulesScreenDestination : BullsCowsDestination {
    override val route = "rulesScreen"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = GameScreenDestination.route,
        modifier = modifier,
    ) {
        composable(route = GameScreenDestination.route) {
            GameScreen()
        }
        composable(route = RulesScreenDestination.route) {
            RulesScreen()
        }
    }
}
