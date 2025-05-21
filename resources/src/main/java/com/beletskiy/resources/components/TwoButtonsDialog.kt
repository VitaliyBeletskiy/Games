package com.beletskiy.resources.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.resources.theme.GamesTheme

@Suppress("detekt:LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoButtonsDialog(
    title: String,
    message: String,
    confirmText: String,
    dismissText: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(dismissText)
            }
        }
    )
}

@Preview
@Composable
private fun TwoButtonsDialogPreview() {
    GamesTheme {
        TwoButtonsDialog(
            title = "Title",
            message = "Text",
            confirmText = "Confirm",
            dismissText = "Dismiss",
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}
