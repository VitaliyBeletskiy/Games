package com.beletskiy.games.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.beletskiy.games.utils.numberToDrawable

@Composable
fun SquareButtonWithNumber(
    modifier: Modifier = Modifier,
    number: Int,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center,
    ) {
        val drawable = numberToDrawable(number)
        Image(
            painterResource(drawable),
            contentDescription = "Number",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
