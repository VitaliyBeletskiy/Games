package com.beletskiy.reversi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.beletskiy.reversi.data.PlayerDisc
import com.beletskiy.reversi.ui.R
import com.beletskiy.shared.theme.Accent
import com.beletskiy.shared.theme.GamesTheme

@Composable
fun ScoreBoardView(
    blackScore: Int,
    whiteScore: Int,
    currentDisc: PlayerDisc,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.score),
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreCircle(disc = PlayerDisc.BLACK, score = blackScore)
            ScoreCircle(disc = PlayerDisc.WHITE, score = whiteScore)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = when (currentDisc) {
                PlayerDisc.BLACK -> "Black’s turn" // FIXME: temporary, shouldn't be a String
                PlayerDisc.WHITE -> "White’s turn" // FIXME: temporary, shouldn't be a String
            },
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun ScoreCircle(
    disc: PlayerDisc,
    score: Int
) {
    val backgroundColor = when (disc) {
        PlayerDisc.BLACK -> Accent
        PlayerDisc.WHITE -> Color.White
    }

    val textColor = if (disc == PlayerDisc.BLACK) Color.White else Accent

    Box(
        modifier = Modifier
            .size(48.dp)
            .background(backgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = score.toString(),
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun PreviewScoreBoard() {
    GamesTheme {
        ScoreBoardView(
            blackScore = 2,
            whiteScore = 3,
            currentDisc = PlayerDisc.BLACK,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}
