package com.beletskiy.games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.beletskiy.bac.ui.BullsAndCowsNav
import com.beletskiy.bac.ui.bullsAndCowsNavGraph
import com.beletskiy.bac.ui.theme.GamesTheme
import com.beletskiy.fifteen.ui.FifteenNav
import com.beletskiy.fifteen.ui.fifteenNavGraph
import com.beletskiy.ttt.ui.TicTacToeNav
import com.beletskiy.ttt.ui.ticTacToeNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamesApp()
        }
    }
}

@Composable
fun GamesApp() {
    GamesTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        "Bulls and Cows" to BullsAndCowsNav.ROUTE,
//        "Reversi" to ReversiNav.ROUTE,
        "Tic Tac Toe" to TicTacToeNav.ROUTE,
        "Fifteen" to FifteenNav.ROUTE
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                drawerItems.forEach { (label, route) ->
                    NavigationDrawerItem(
                        label = { Text(label) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigateAndReplace(route)
                            }
                        }
                    )
                }
            }
        }
    ) {
        AppNavHost(navController)
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BullsAndCowsNav.ROUTE) {
        bullsAndCowsNavGraph(navController)
//        reversiNavGraph(navController)
        ticTacToeNavGraph(navController)
        fifteenNavGraph(navController)
    }
}

fun NavController.navigateAndReplace(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
    }
}