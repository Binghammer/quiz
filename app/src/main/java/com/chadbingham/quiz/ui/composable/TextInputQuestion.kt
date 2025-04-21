/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun TextInputQuestion(
    modifier: Modifier = Modifier,
    question: Question.TextInput,
    answer: UserAnswer.TextInput,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = answer.text,

            onValueChange = {
                onAnswerSelected(UserAnswer.TextInput(it))
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
            ),
            placeholder = {
                Text(question.hint)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTextInput() {
    CricutQuizTheme {
        TextInputQuestion(
            modifier = Modifier,
            question = PreviewQuizState.getUserInputQuestion(),
            answer = UserAnswer.TextInput()
        ) { }
    }

}