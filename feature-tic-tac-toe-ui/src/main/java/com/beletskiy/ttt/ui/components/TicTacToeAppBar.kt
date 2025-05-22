package com.beletskiy.ttt.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.shared.theme.GamesTheme
import com.beletskiy.ttt.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onMenu: () -> Unit = {},
    onResetScore: () -> Unit = {},
    onEditNames: () -> Unit = {},
    onSelectGameType: () -> Unit = {},
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
                        text = { Text(stringResource(R.string.reset_score)) },
                        onClick = {
                            menuExpanded = false
                            onResetScore()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.edit_names)) },
                        onClick = {
                            menuExpanded = false
                            onEditNames()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.select_game_type)) },
                        onClick = {
                            menuExpanded = false
                            onSelectGameType()
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TicTacToeAppBarPreview() {
    GamesTheme {
        Scaffold(
            topBar = { TicTacToeAppBar(stringResource(R.string.tic_tac_toe_game_title)) },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) { }
        }
    }
}
