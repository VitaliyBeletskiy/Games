package com.beletskiy.ttt.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.beletskiy.shared.theme.GamesTheme
import com.beletskiy.ttt.ui.R

private const val MAX_NAME_LENGTH = 10

@Suppress("detekt:LongMethod")
@Composable
fun EditNamesDialog(
    player1Name: String,
    player2Name: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmation: (String, String) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current
    var name1 by remember { mutableStateOf(player1Name) }
    var name2 by remember { mutableStateOf(player2Name) }
    val name1Error = remember {
        derivedStateOf {
            if (name1.isBlank()) {
                context.getString(R.string.name_cannot_be_empty)
            } else if (name1.length > MAX_NAME_LENGTH) {
                context.getString(R.string.name_is_too_long)
            } else {
                null
            }
        }
    }
    val name2Error = remember {
        derivedStateOf {
            if (name2.isBlank()) {
                context.getString(R.string.name_cannot_be_empty)
            } else if (name2.length > MAX_NAME_LENGTH) {
                context.getString(R.string.name_is_too_long)
            } else {
                null
            }
        }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier.wrapContentSize(),
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.edit_player_names),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                )

                OutlinedTextField(
                    value = name1,
                    onValueChange = {
                        name1 = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    isError = name1Error.value != null,
                    label = { Text(stringResource(R.string.player_1)) },
                    singleLine = true,
                )
                name1Error.value?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(top = 4.dp, start = 24.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = name2,
                    onValueChange = {
                        name2 = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    isError = name2Error.value != null,
                    label = { Text(stringResource(R.string.player_2)) },
                    singleLine = true,
                )
                name2Error.value?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(top = 4.dp, start = 24.dp),
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.dismiss))
                    }
                    TextButton(
                        onClick = { onConfirmation(name1, name2) },
                        enabled = name1Error.value == null && name2Error.value == null,
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditNamesDialogPreview() {
    GamesTheme {
        EditNamesDialog(
            player1Name = "Player's name",
            player2Name = "Player 2",
            onDismissRequest = {},
            onConfirmation = { _, _ -> },
        )
    }
}
