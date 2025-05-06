package com.beletskiy.bac.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.beletskiy.bac.ui.R

@Composable
fun ConfirmationDialog(
    title: String = "",
    message: String = "",
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        containerColor = colorResource(id = R.color.background_field),
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(containerColor = colorResource(id = R.color.background_main)),
            ) {
                Text("Ok", color = colorResource(id = R.color.text))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                colors = ButtonDefaults.textButtonColors(containerColor = colorResource(id = R.color.background_main)),
            ) {
                Text("Cancel", color = colorResource(id = R.color.text))
            }
        },
    )
}
