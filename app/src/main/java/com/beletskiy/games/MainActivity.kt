package com.beletskiy.games

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.beletskiy.bac.ui.BullsAndCowsNav
import com.beletskiy.bac.ui.bullsAndCowsNavGraph
import com.beletskiy.fifteen.ui.FifteenNav
import com.beletskiy.fifteen.ui.fifteenNavGraph
import com.beletskiy.reversi.ui.ReversiNav
import com.beletskiy.reversi.ui.reversiNavGraph
import com.beletskiy.shared.theme.GamesTheme
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
        stringResource(R.string.bulls_and_cows_game_title) to BullsAndCowsNav.ROUTE,
        stringResource(R.string.tic_tac_toe_game_title) to TicTacToeNav.ROUTE,
        stringResource(R.string.fifteen_game_title) to FifteenNav.ROUTE,
        stringResource(R.string.reversi_game_title) to ReversiNav.ROUTE,
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp),
                drawerContainerColor = MaterialTheme.colorScheme.background, //Color(0xFFF2DEDA), //MaterialTheme.colorScheme.surface,
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    HorizontalDivider()

                    drawerItems.forEach { (label, route) ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            },
                            selected = false,
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    navController.navigateAndReplace(route)
                                }
                            }
                        )
                    }
                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = "Switching games will reset the current one for now — we're working on improving this.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    ) {
        AppNavHost(navController) {
            scope.launch {
                drawerState.open()
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, onMenuClick: () -> Unit) {
    NavHost(navController = navController, startDestination = BullsAndCowsNav.ROUTE) {
        bullsAndCowsNavGraph(navController, onMenuClick)
        ticTacToeNavGraph(navController, onMenuClick)
        fifteenNavGraph(navController, onMenuClick)
        reversiNavGraph(navController, onMenuClick)
    }
}

fun NavController.navigateAndReplace(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
        launchSingleTop = true
    }
}

@Preview
@Composable
private fun GamesAppPreview() {
    GamesTheme {
        MainScreen()
    }
}
