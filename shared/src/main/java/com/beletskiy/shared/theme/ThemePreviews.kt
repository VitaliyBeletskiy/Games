package com.beletskiy.shared.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ThemedDialogPreview() {
    GamesTheme {
        var open by remember { mutableStateOf(true) }

        if (open) {
            AlertDialog(
                onDismissRequest = { open = false },
                shape = RoundedCornerShape(20.dp),
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                textContentColor = MaterialTheme.colorScheme.onBackground,
                confirmButton = {
                    TextButton(onClick = { open = false }) {
                        Text(
                            text = "Confirm",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { open = false }) {
                        Text(
                            text = "Cancel",
//                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                title = {
                    Text(
                        text = "Reset the game?",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Text(
                        text = "Your current score will be lost. Do you want to continue?",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    }
}
