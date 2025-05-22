package com.beletskiy.bac.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.bac.ui.BullsAndCowsScreen
import com.beletskiy.bac.ui.R
import com.beletskiy.resources.theme.GamesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BullsAndCowsAppBar(
    screen: BullsAndCowsScreen,
    isGameOver: Boolean = false,
    onMenu: () -> Unit = {},
    onNavigateUp: () -> Unit = {},
    onNavigateToRules: () -> Unit = {},
    onRestart: () -> Unit = {},
) {
    val title = when {
        screen == BullsAndCowsScreen.Rules -> stringResource(id = R.string.rules_caption)
        isGameOver -> stringResource(id = R.string.you_win)
        else -> stringResource(id = R.string.bulls_and_cows_game_title)
    }
    CenterAlignedTopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            if (screen == BullsAndCowsScreen.Game) {
                IconButton(onClick = onMenu) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.description_menu),
                    )
                }
            } else {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.description_back),
                    )
                }
            }
        },
        actions = {
            if (screen == BullsAndCowsScreen.Game) {
                IconButton(onClick = onRestart) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_restart),
                        contentDescription = stringResource(id = R.string.description_restart),
                    )
                }
                IconButton(onClick = onNavigateToRules) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_rules),
                        contentDescription = stringResource(id = R.string.description_rules),
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun AppBarPreviewGameScreen() {
    GamesTheme {
        BullsAndCowsAppBar(
            screen = BullsAndCowsScreen.Game,
        )
    }
}

@Preview
@Composable
private fun AppBarPreviewRulesScreen() {
    GamesTheme {
        BullsAndCowsAppBar(
            screen = BullsAndCowsScreen.Rules,
        )
    }
}