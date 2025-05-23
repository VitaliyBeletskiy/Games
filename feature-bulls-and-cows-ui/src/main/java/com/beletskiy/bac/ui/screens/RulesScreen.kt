package com.beletskiy.bac.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beletskiy.bac.ui.BullsAndCowsScreen
import com.beletskiy.bac.ui.R
import com.beletskiy.bac.ui.components.BullsAndCowsAppBar
import com.beletskiy.shared.theme.GamesTheme

@Composable
fun RulesScreen(onNavigateUp: () -> Unit = {}) {
    Scaffold(
        topBar = {
            BullsAndCowsAppBar(screen = BullsAndCowsScreen.Rules, onNavigateUp = onNavigateUp)
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            val textSize = 16.sp
            val annotatedString = makeAnnotatedStringForRules()
            val inlineContentMap = mapOf(
                "img1" to InlineTextContent(
                    Placeholder(textSize, textSize, PlaceholderVerticalAlign.TextCenter),
                ) {
                    Image(
                        painterResource(R.drawable.ic_bull),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "",
                    )
                },
                "img2" to InlineTextContent(
                    Placeholder(textSize, textSize, PlaceholderVerticalAlign.TextCenter),
                ) {
                    Image(
                        painterResource(R.drawable.ic_cow),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "",
                    )
                },
            )

            Text(annotatedString, inlineContent = inlineContentMap, fontSize = textSize)
        }
    }
}

// FIXME: straight and stupid, to refactor
@Composable
private fun makeAnnotatedStringForRules(): AnnotatedString {
    val string = stringResource(id = R.string.rules_text)
    val regex = "img[1-9]+".toRegex()
    val numberOfImg = regex.findAll(string).count()
    val list = string.split(regex = regex)
    val annotatedString = buildAnnotatedString {
        append(list.first())
        for (i in 1..numberOfImg) {
            appendInlineContent(id = "img$i")
            append(list[i])
        }
        list.getOrNull(numberOfImg + 1)?.let { append(it) }
    }
    return annotatedString
}

@Preview(showBackground = true, widthDp = 320, heightDp = 722)
@Composable
private fun RulesScreenPreview() {
    GamesTheme {
        RulesScreen()
    }
}
