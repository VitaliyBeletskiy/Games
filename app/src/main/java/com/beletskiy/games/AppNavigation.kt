package com.beletskiy.games

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.beletskiy.bac.ui.BullsAndCowsSectionHost
import com.beletskiy.fifteen.ui.FifteenGameRoute
import com.beletskiy.reversi.ui.ReversiGameRoute
import com.beletskiy.ttt.ui.TicTacToeGameRoute
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.collections.listOf

private object GameRoutes {

    object BullAndCows {
        @Serializable
        object Graph
    }

    @Serializable
    object TicTacToe

    @Serializable
    object Fifteen

    @Serializable
    object Reversi
}

private data class DrawerItem<R : Any>(
    val label: String,
    val route: R,
    val isSelected: (NavDestination?) -> Boolean,
)

@Composable
internal fun GameAppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val drawerItems = listOf(
        DrawerItem(
            label = stringResource(R.string.bulls_and_cows_game_title),
            route = GameRoutes.BullAndCows.Graph,
        ) { dest -> dest?.hasRoute<GameRoutes.BullAndCows.Graph>() == true },
        DrawerItem(
            label = stringResource(R.string.tic_tac_toe_game_title),
            route = GameRoutes.TicTacToe,
        ) { dest -> dest?.hasRoute<GameRoutes.TicTacToe>() == true },
        DrawerItem(
            label = stringResource(R.string.fifteen_game_title),
            route = GameRoutes.Fifteen,
        ) { dest -> dest?.hasRoute<GameRoutes.Fifteen>() == true },
        DrawerItem(
            label = stringResource(R.string.reversi_game_title),
            route = GameRoutes.Reversi,
        ) { dest -> dest?.hasRoute<GameRoutes.Reversi>() == true },
    )

    val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }
    val closeDrawer: () -> Unit = { scope.launch { drawerState.close() } }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp),
                drawerContainerColor = MaterialTheme.colorScheme.background,
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    HorizontalDivider()

                    drawerItems.forEach {
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = it.label,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            },
                            selected = it.isSelected(currentDestination),
                            onClick = {
                                closeDrawer()
                                navController.navigate(it.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            },
                        )
                    }
                }
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = GameRoutes.BullAndCows.Graph,
        ) {
            composable<GameRoutes.BullAndCows.Graph> {
                BullsAndCowsSectionHost(
                    openDrawer = openDrawer
                )
            }

            composable<GameRoutes.TicTacToe> {
                TicTacToeGameRoute(openDrawer = openDrawer)
            }

            composable<GameRoutes.Fifteen> {
                FifteenGameRoute(openDrawer = openDrawer)
            }

            composable<GameRoutes.Reversi> {
                ReversiGameRoute(openDrawer = openDrawer)
            }
        }
    }
}
