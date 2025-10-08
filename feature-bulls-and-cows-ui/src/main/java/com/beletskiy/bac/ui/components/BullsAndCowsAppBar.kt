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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.bac.ui.R
import com.beletskiy.shared.theme.GamesTheme

internal enum class AppBarRole { Game, Rules }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BullsAndCowsAppBar(
    title: String,
    appBarRole: AppBarRole,
    onNavClick: () -> Unit,
    onRestart: (() -> Unit)? = null,
    onRules: (() -> Unit)? = null,
) {
    val restartPainter = rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_restart))
    val rulesPainter = rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_rules))

    CenterAlignedTopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            when (appBarRole) {
                AppBarRole.Game -> IconButton(onClick = onNavClick) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.description_menu)
                    )
                }

                AppBarRole.Rules -> IconButton(onClick = onNavClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.description_back)
                    )
                }
            }
        },
        actions = {
            if (onRestart != null) {
                IconButton(onClick = onRestart) {
                    Icon(
                        painter = restartPainter,
                        contentDescription = stringResource(R.string.description_restart),
                    )
                }
            }
            if (onRules != null) {
                IconButton(onClick = onRules) {
                    Icon(
                        painter = rulesPainter,
                        contentDescription = stringResource(R.string.description_rules),
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun AppBarPreviewGameScreen() {
    GamesTheme {
        BullsAndCowsAppBar(
            title = stringResource(id = R.string.bulls_and_cows_game_title),
            appBarRole = AppBarRole.Game,
            onNavClick = {},
            onRestart = {},
            onRules = {},
        )
    }
}

@Preview
@Composable
private fun AppBarPreviewRulesScreen() {
    GamesTheme {
        BullsAndCowsAppBar(
            title = stringResource(id = R.string.rules_caption),
            appBarRole = AppBarRole.Rules,
            onNavClick = {},
            onRestart = null,
            onRules = null,
        )
    }
}
