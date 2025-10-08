package com.beletskiy.bac.ui

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.beletskiy.bac.ui.components.BullsAndCowsAppBar
import com.beletskiy.bac.ui.components.AppBarRole
import com.beletskiy.bac.ui.screens.RulesScreen
import com.beletskiy.bac.ui.screens.GameField
import com.beletskiy.bac.ui.screens.GameViewModel
import com.beletskiy.bac.ui.screens.Message
import com.beletskiy.shared.components.TwoButtonsDialog
import kotlinx.serialization.Serializable

private object BullsAndCowsRoutes {
    @Serializable
    object Game

    @Serializable
    object Rules
}

@Composable
fun BullsAndCowsSectionHost(
    openDrawer: () -> Unit,
) {
    var hasGuesses by remember { mutableStateOf(false) }
    var isGameOver by remember { mutableStateOf(false) }
    var onRestartAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    var askRestart by remember { mutableStateOf(false) }
    fun requestRestart() {
        if (hasGuesses && !isGameOver) askRestart = true else onRestartAction?.invoke()
    }

    val context = LocalContext.current
    val showMessage: (String) -> Unit = remember(context) {
        { text ->
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    // region Setting Up BullsAndCowsAppBar
    val sectionNavController = rememberNavController()
    val currentEntry by sectionNavController.currentBackStackEntryAsState()
    val appBarRole =
        if (currentEntry?.destination?.hasRoute<BullsAndCowsRoutes.Rules>() == true)
            AppBarRole.Rules
        else
            AppBarRole.Game
    val title = when {
        appBarRole == AppBarRole.Rules -> stringResource(R.string.rules_caption)
        isGameOver -> stringResource(R.string.you_win)
        else -> stringResource(R.string.bulls_and_cows_game_title)
    }
    // endregion Setting Up BullsAndCowsAppBar

    Scaffold(
        topBar = {
            BullsAndCowsAppBar(
                title = title,
                appBarRole = appBarRole,
                onNavClick = {
                    if (appBarRole == AppBarRole.Game) {
                        openDrawer()
                    } else {
                        sectionNavController.navigateUp() // Boolean вернётся, но мы его игнорируем
                    }
                },
                onRestart = if (appBarRole == AppBarRole.Game) ::requestRestart else null,
                onRules = if (appBarRole == AppBarRole.Game) {
                    { sectionNavController.navigate(BullsAndCowsRoutes.Rules) }
                } else null,
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = sectionNavController,
            startDestination = BullsAndCowsRoutes.Game,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<BullsAndCowsRoutes.Game> {
                BullsAndCowsGameRoute(
                    onHasGuessesChanged = { hasGuesses = it },
                    onIsGameOverChanged = { isGameOver = it },
                    onRegisterRestart = { restart -> onRestartAction = restart },
                    showMessage = showMessage,
                )
            }
            composable<BullsAndCowsRoutes.Rules> {
                BullsAndCowsRulesRoute()
            }
        }
    }

    when {
        askRestart -> {
            TwoButtonsDialog(
                title = "Restart game",
                message = "Are you sure you want to restart the game?",
                confirmText = stringResource(R.string.confirm),
                dismissText = stringResource(R.string.dismiss),
                onDismissRequest = { askRestart = false },
                onConfirmation = {
                    askRestart = false
                    onRestartAction?.invoke()
                },
            )
        }
    }
}

@Composable
private fun BullsAndCowsGameRoute(
    onHasGuessesChanged: (Boolean) -> Unit,
    onIsGameOverChanged: (Boolean) -> Unit,
    onRegisterRestart: (restart: () -> Unit) -> Unit,
    showMessage: (String) -> Unit,
    viewModel: GameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.gameUiState.collectAsStateWithLifecycle()

    uiState.message?.let { message ->
        val text = when (message) {
            Message.MUST_CONTAIN_UNIQUE_NUMBERS_WARNING -> stringResource(id = R.string.must_contain_unique_numbers)
        }
        LaunchedEffect(message) {
            showMessage(text)
            viewModel.onMessageShown()
        }
    }

    LaunchedEffect(uiState.guesses.size, uiState.isGameOver) {
        onHasGuessesChanged(uiState.guesses.isNotEmpty())
        onIsGameOverChanged(uiState.isGameOver)
    }
    DisposableEffect(viewModel) {
        onRegisterRestart { viewModel.restart() }
        onDispose { onRegisterRestart {} }
    }

    GameField(
        uiState = uiState,
        onGuess = viewModel::evaluateUserInput,
    )
}

@Composable
private fun BullsAndCowsRulesRoute() {
    RulesScreen()
}
