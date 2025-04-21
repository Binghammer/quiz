/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chadbingham.quiz.data.Question
import com.chadbingham.quiz.data.UserAnswer
import com.chadbingham.quiz.ui.theme.CricutQuizTheme
import com.chadbingham.quiz.util.PreviewQuizState

@Composable
fun SingleChoiceQuestion(
    modifier: Modifier = Modifier,
    question: Question.SingleChoice,
    answer: UserAnswer.SingleChoice,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        question.options.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(vertical = 4.dp)
                    .clickable {
                        onAnswerSelected(UserAnswer.SingleChoice(index))
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = answer.index == index,
                    onClick = null
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = option
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSingle() {
    CricutQuizTheme {
        SingleChoiceQuestion(
            modifier = Modifier,
            question = PreviewQuizState.getSingleChoiceQuestion(),
            answer = UserAnswer.SingleChoice()
        ) { }
    }
}