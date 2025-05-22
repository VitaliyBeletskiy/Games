package com.beletskiy.bac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.bac.ui.R
import com.beletskiy.shared.theme.GamesTheme

@Composable
fun GuessButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(20)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        val iconSize = (minOf(this.maxWidth, maxHeight) * 0.7f)

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = stringResource(R.string.button_guess_text),
            modifier = Modifier.size(iconSize),
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(heightDp = 100, widthDp = 100)
@Composable
private fun GuessButtonPreview() {
    GamesTheme {
        GuessButton(
            modifier = Modifier.background(Color.Red),
            onClick = {}
        )
    }
}
