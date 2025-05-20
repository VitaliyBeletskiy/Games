package com.beletskiy.bac.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.beletskiy.bac.data.GuessResult
import com.beletskiy.bac.ui.utils.guessResultToDrawable

@Composable
fun GuessResultsView(results: List<GuessResult>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        results.forEach { guessResult ->
            val drawable = guessResultToDrawable(guessResult)
            Box(modifier = Modifier.size(20.dp)) {
                Image(
                    painter = painterResource(drawable),
                    contentDescription = "Guess Result",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}

@Preview
@Composable
fun GuessResultsViewPreview() {
    GuessResultsView(
        results = listOf(
            GuessResult.BULL,
            GuessResult.COW,
            GuessResult.NOTHING,
            GuessResult.NOTHING,
        ),
    )
}
