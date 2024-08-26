package com.beletskiy.bullscows.compose.ui.game

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.beletskiy.bullscows.compose.BullsAndCowsApp
import com.beletskiy.bullscows.compose.GameScreen
import com.beletskiy.bullscows.compose.ui.theme.BullsCowsTheme

@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Greeting("Hello Android")
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier,
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 722)
@Composable
fun GreetingPreview() {
    BullsCowsTheme {
        BullsAndCowsApp()
    }
}
