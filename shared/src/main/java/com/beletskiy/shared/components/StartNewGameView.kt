package com.beletskiy.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.shared.R
import com.beletskiy.shared.theme.GamesTheme

@Composable
fun StartNewGameView(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.start_new_game),
    onClick: () -> Unit) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onClick) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 722)
@Composable
private fun StartNewGameViewPreview() {
    GamesTheme {
        StartNewGameView() {}
    }
}
