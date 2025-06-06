package com.beletskiy.bac.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beletskiy.bac.data.Guess
import com.beletskiy.bac.ui.BullsAndCowsScreen
import com.beletskiy.bac.ui.R
import com.beletskiy.bac.ui.components.BullsAndCowsAppBar
import com.beletskiy.bac.ui.components.GuessButton
import com.beletskiy.bac.ui.components.GuessResultsView
import com.beletskiy.bac.ui.components.NumberPickerDialog
import com.beletskiy.bac.ui.components.SquareButtonWithNumber
import com.beletskiy.shared.components.TwoButtonsDialog
import com.beletskiy.shared.theme.GamesTheme
import kotlinx.coroutines.delay

private const val LIST_ANIMATION_DELAY = 100L

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onMenuClick: () -> Unit = {},
    onNavigateUp: () -> Unit = {},
    onNavigateToRules: () -> Unit = {},
) {
    val uiState by viewModel.gameUiState.collectAsState()
    val openRestartDialog = remember { mutableStateOf(false) }

    when {
        openRestartDialog.value -> {
            TwoButtonsDialog(
                title = "Restart game",
                message = "Are you sure you want to restart the game?",
                confirmText = stringResource(R.string.confirm),
                dismissText = stringResource(R.string.dismiss),
                onDismissRequest = { openRestartDialog.value = false },
                onConfirmation = {
                    openRestartDialog.value = false
                    viewModel.restart()
                },
            )
        }
    }

    Scaffold(
        topBar = {
            BullsAndCowsAppBar(
                screen = BullsAndCowsScreen.Game,
                isGameOver = uiState.isGameOver,
                onMenu = onMenuClick,
                onNavigateUp = onNavigateUp,
                onNavigateToRules = onNavigateToRules,
            ) {
                if (viewModel.canRestartSilently) {
                    viewModel.restart()
                } else {
                    openRestartDialog.value = true
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            GuessList(modifier = Modifier.weight(1f), guesses = uiState.guesses)

            if (!uiState.isGameOver) {
                UserInputPanel { userInput ->
                    viewModel.evaluateUserInput(userInput)
                }
            }
        }

        uiState.message?.let { message ->
            val text = when (message) {
                Message.MUST_CONTAIN_UNIQUE_NUMBERS_WARNING -> stringResource(id = R.string.must_contain_unique_numbers)
            }

            Toast.makeText(LocalContext.current, text, Toast.LENGTH_SHORT).show()
            LaunchedEffect(message) {
                viewModel.onMessageShown()
            }
        }
    }
}

@Composable
private fun GuessList(modifier: Modifier = Modifier, guesses: List<Guess>) {
    val listState = rememberLazyListState()

    LaunchedEffect(guesses.size) {
        if (guesses.isNotEmpty()) {
            delay(LIST_ANIMATION_DELAY)
            listState.animateScrollToItem(guesses.size - 1)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(count = guesses.size, key = { idx -> guesses[idx].ordinal }) { idx ->
            GuessListItem(guess = guesses[idx])
        }
    }
}

@Composable
private fun GuessListItem(guess: Guess) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        GuessOrdinalView(guess.ordinal.toString())
        SquareButtonWithNumber(Modifier.weight(1f), number = guess.userInput[0])
        SquareButtonWithNumber(Modifier.weight(1f), number = guess.userInput[1])
        SquareButtonWithNumber(Modifier.weight(1f), number = guess.userInput[2])
        SquareButtonWithNumber(Modifier.weight(1f), number = guess.userInput[3])
        GuessResultsView(guess.guessResults)
    }
}

@Composable
private fun GuessOrdinalView(ordinal: String) {
    Box(
        modifier = Modifier
            .size(25.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = ordinal)
    }
}

@Composable
private fun UserInputPanel(onTryClick: (List<Int>) -> Unit) {
    val pickedNumbers = rememberSaveable { mutableListOf(1, 2, 3, 4) }
    val currentPicker = rememberSaveable { mutableIntStateOf(0) }
    val openDialogWithNumbers = rememberSaveable { mutableStateOf(false) }
    when {
        openDialogWithNumbers.value -> {
            NumberPickerDialog(
                onDismissRequest = { openDialogWithNumbers.value = false },
                onNumberClicked = { chosenNumber ->
                    openDialogWithNumbers.value = false
                    pickedNumbers[currentPicker.intValue] = chosenNumber
                },
            )
        }
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SquareButtonWithNumber(Modifier.weight(1f), pickedNumbers[0]) {
                currentPicker.intValue = 0
                openDialogWithNumbers.value = true
            }
            SquareButtonWithNumber(Modifier.weight(1f), pickedNumbers[1]) {
                currentPicker.intValue = 1
                openDialogWithNumbers.value = true
            }
            SquareButtonWithNumber(Modifier.weight(1f), pickedNumbers[2]) {
                currentPicker.intValue = 2
                openDialogWithNumbers.value = true
            }
            SquareButtonWithNumber(Modifier.weight(1f), pickedNumbers[3]) {
                currentPicker.intValue = 3
                openDialogWithNumbers.value = true
            }
            GuessButton(Modifier.weight(1f)) {
                onTryClick.invoke(pickedNumbers.toList())
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 722)
@Composable
private fun GameScreenPreview() {
    GamesTheme {
        GameScreen(hiltViewModel<GameViewModel>())
    }
}
