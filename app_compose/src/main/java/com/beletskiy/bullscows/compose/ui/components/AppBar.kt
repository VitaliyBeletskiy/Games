package com.beletskiy.bullscows.compose.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.beletskiy.bullscows.compose.AppScreens
import com.beletskiy.bullscows.compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    screen: AppScreens,
    isGameOver: Boolean = false,
    onNavigateUp: () -> Unit = {},
    onNavigateToRules: () -> Unit = {},
    onRestart: () -> Unit = {},
) {
    val title = when {
        screen == AppScreens.RulesScreen -> stringResource(id = R.string.rules_caption)
        isGameOver -> stringResource(id = R.string.caption_when_game_is_over)
        else -> stringResource(id = R.string.app_name)
    }

    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(id = R.color.background_main),
            titleContentColor = colorResource(id = R.color.text),
        ),
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            if (screen == AppScreens.GameScreen) return@TopAppBar
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.description_back),
                )
            }
        },
        actions = {
            if (screen == AppScreens.GameScreen) {
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
