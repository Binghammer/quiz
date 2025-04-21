/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.chadbingham.quiz.data.UserAnswer
import com.chadbingham.quiz.ui.theme.CricutQuizTheme

@Composable
fun TrueFalseQuestion(
    modifier: Modifier = Modifier,
    answer: UserAnswer.TrueFalse,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onAnswerSelected(UserAnswer.TrueFalse(true)) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (answer.answer) Color.Green else Color.LightGray
                )
            ) {
                Text(text = "True")
            }

            Button(
                onClick = { onAnswerSelected(UserAnswer.TrueFalse(false)) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!answer.answer) Color.Red else Color.LightGray
                )
            ) {
                Text(text = "False")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTrueFalse() {
    CricutQuizTheme {
        TrueFalseQuestion(
            modifier = Modifier,
            answer = UserAnswer.TrueFalse()
        ) { }
    }
}