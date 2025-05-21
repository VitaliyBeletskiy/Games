package com.beletskiy.resources.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.resources.R
import com.beletskiy.resources.theme.GamesTheme

@Suppress("detekt:LongParameterList")
@Composable
fun SegmentedButtonsDialog(
    title: String,
    labels: List<String>,
    initialIndex: Int,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmation: (Int) -> Unit = {},
) {
    var selected by remember { mutableIntStateOf(initialIndex) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = {
            SingleChoiceSegmentedButton(
                labels = labels,
                selected = selected,
                onSelectedChange = {
                    selected = it
                }
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation(selected) },
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(stringResource(R.string.dismiss))
            }
        },
    )
}

@Composable
private fun SingleChoiceSegmentedButton(
    labels: List<String>,
    selected: Int,
    modifier: Modifier = Modifier,
    onSelectedChange: (Int) -> Unit = {},
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier,
    ) {
        labels.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = labels.size
                ),
                onClick = { onSelectedChange(index) },
                selected = index == selected,
                label = { Text(label) }
            )
        }
    }
}

@Preview
@Composable
private fun SegmentedButtonsDialogDialogPreview() {
    GamesTheme {
        SegmentedButtonsDialog(
            title = "Select Game Type",
            labels = listOf("Classic", "Dynamic"),
            initialIndex = 0,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}
