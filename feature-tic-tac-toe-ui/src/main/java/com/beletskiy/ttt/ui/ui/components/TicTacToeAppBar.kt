package com.beletskiy.ttt.ui.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import com.beletskiy.ttt.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onResetScoreClicked: () -> Unit = {},
    onEditNamesClicked: () -> Unit = {},
) {
    var menuExpanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(title)
        },
        modifier = modifier,
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
                            onResetScoreClicked()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.edit_names)) },
                        onClick = {
                            menuExpanded = false
                            onEditNamesClicked()
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBarWithDropdown() {
    Scaffold(
        topBar = { TicTacToeAppBar(stringResource(R.string.game_title)) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) { }
    }
}
