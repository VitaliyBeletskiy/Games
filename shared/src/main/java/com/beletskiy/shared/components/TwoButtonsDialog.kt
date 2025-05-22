package com.beletskiy.shared.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.shared.theme.GamesTheme

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
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        textContentColor = MaterialTheme.colorScheme.onBackground,
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = message, style = MaterialTheme.typography.bodyLarge)
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
                Text(confirmText, style = MaterialTheme.typography.labelLarge)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(dismissText, style = MaterialTheme.typography.labelLarge)
            }
        }
    )
}

@Preview
@Composable
private fun TwoButtonsDialogPreview() {
    GamesTheme {
        TwoButtonsDialog(
            title = "Reset the game?",
            message = "Your current score will be lost. Do you want to continue?",
            confirmText = "Confirm",
            dismissText = "Dismiss",
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}
