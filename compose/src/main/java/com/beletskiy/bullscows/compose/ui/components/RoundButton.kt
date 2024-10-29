package com.beletskiy.bullscows.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.beletskiy.bullscows.compose.R

@Composable
fun RoundButton(
    modifierWithWeight: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifierWithWeight
            .aspectRatio(1f)
            .background(color = colorResource(id = R.color.button), shape = CircleShape)
            .clickable {
                onClick.invoke()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = stringResource(R.string.button_try_text), fontSize = 24.sp)
    }
}
