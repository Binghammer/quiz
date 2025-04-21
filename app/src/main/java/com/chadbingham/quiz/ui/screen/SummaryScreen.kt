/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chadbingham.quiz.R
import com.chadbingham.quiz.ui.theme.CricutQuizTheme
import com.chadbingham.quiz.ui.theme.Typography

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    startOver: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.congrats),
                style = Typography.titleLarge
            )

            Spacer(modifier.size(16.dp))

            Text(
                stringResource(R.string.you_completed_the_quiz),
                style = Typography.bodyMedium
            )
        }

        Button(
            onClick = { startOver() }
        ) {
            Text(text = stringResource(R.string.start_over))
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSummaryScreen() {
    CricutQuizTheme {
        SummaryScreen(Modifier) {}
    }
}