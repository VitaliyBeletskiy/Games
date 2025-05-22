package com.beletskiy.bac.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.beletskiy.shared.theme.GamesTheme

private val buttonModifier = Modifier.padding(vertical = 4.dp)

@Composable
fun NumberPickerDialog(
    onDismissRequest: () -> Unit = {},
    onNumberClicked: (Int) -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.7f),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 1,
                    ) { onNumberClicked.invoke(1) }
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 2,
                    ) { onNumberClicked.invoke(2) }
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 3,
                    ) { onNumberClicked.invoke(3) }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 4,
                    ) { onNumberClicked.invoke(4) }
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 5,
                    ) { onNumberClicked.invoke(5) }
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 6,
                    ) { onNumberClicked.invoke(6) }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 7,
                    ) { onNumberClicked.invoke(7) }
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 8,
                    ) { onNumberClicked.invoke(8) }
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 9,
                    ) { onNumberClicked.invoke(9) }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f).alpha(0f),
                        number = 0,
                    )
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f),
                        number = 0,
                    ) { onNumberClicked.invoke(0) }
                    SquareButtonWithNumber(
                        modifier = buttonModifier.weight(1f).alpha(0f),
                        number = 0,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 722)
@Composable
private fun DialogWithNumbersPreview() {
    GamesTheme {
        NumberPickerDialog()
    }
}
