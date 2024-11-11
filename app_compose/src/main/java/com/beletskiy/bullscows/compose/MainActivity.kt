package com.beletskiy.bullscows.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.beletskiy.bullscows.compose.ui.theme.BullsCowsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BullsAndCowsApp()
        }
    }
}

@Composable
fun BullsAndCowsApp() {
    BullsCowsTheme {
        AppNavigation()
    }
}
