package com.beletskiy.fifteen.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.fifteen.ui.R
import com.beletskiy.resources.theme.GamesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FifteenAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onMenu: () -> Unit = {},
    onRestartGame: () -> Unit = {},
    onSelectMoveType: () -> Unit = {},
) {
    var menuExpanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(title)
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onMenu) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(id = R.string.description_menu),
                )
            }
        },
        actions = {
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more_options)
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.restart_game)) },
                        onClick = {
                            menuExpanded = false
                            onRestartGame()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.select_move_type)) },
                        onClick = {
                            menuExpanded = false
                            onSelectMoveType()
                        }
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
        FifteenAppBar(
            title = stringResource(id = R.string.fifteen_game_title),
        )
    }
}
