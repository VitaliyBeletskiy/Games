package com.beletskiy.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.beletskiy.shared.theme.GamesTheme
import androidx.compose.ui.window.Dialog

@Composable
fun SegmentedButtonsDialog(
    title: String,
    labels: List<String>,
    initialIndex: Int,
    onDismissRequest: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    var selected by remember { mutableIntStateOf(initialIndex) }

    Dialog(onDismissRequest = onDismissRequest) {

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 4.dp,
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    modifier = Modifier.width(IntrinsicSize.Max).align(Alignment.Start),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(20.dp))

                SingleChoiceSegmentedButtons(
                    labels = labels,
                    selected = selected,
                    onSelectedChange = { selected = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Dismiss", style = MaterialTheme.typography.labelLarge)
                    }
                    TextButton(onClick = { onConfirm(selected) }) {
                        Text("Confirm", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}

@Composable
private fun SingleChoiceSegmentedButtons(
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
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = MaterialTheme.colorScheme.surface,
                    activeContentColor = MaterialTheme.colorScheme.onBackground,
                    inactiveContainerColor = Color.Transparent,
                    inactiveContentColor = MaterialTheme.colorScheme.onBackground
                ),
                onClick = { onSelectedChange(index) },
                selected = index == selected,
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = if (selected == index) Color.White else MaterialTheme.colorScheme.onBackground
                        )
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SegmentedButtonsDialogDialogPreview() {
    GamesTheme {
        SegmentedButtonsDialog(
            title = "Select Game Type",
            labels = listOf("Classic", "Dynamic"),
            initialIndex = 0,
            onDismissRequest = {},
            onConfirm = {},
        )
    }
}
