package com.beletskiy.ttt.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Suppress("detekt:LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwoButtonsDialog(
    dialogTitle: String,
    dialogText: String,
    confirmText: String,
    dismissText: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
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

@Suppress("detekt:UnusedPrivateMember")
@Preview
@Composable
private fun TwoButtonsDialogPreview() {
    TwoButtonsDialog(
        dialogTitle = "Title",
        dialogText = "Text",
        confirmText = "Confirm",
        dismissText = "Dismiss",
        onDismissRequest = {},
        onConfirmation = {}
    )
}
