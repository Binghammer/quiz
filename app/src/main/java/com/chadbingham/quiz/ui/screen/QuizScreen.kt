/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.ui.screen

import MultipleChoiceQuestion
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chadbingham.quiz.R
import com.chadbingham.quiz.data.Question
import com.chadbingham.quiz.data.QuestionType.TRUE_FALSE
import com.chadbingham.quiz.data.UserAnswer
import com.chadbingham.quiz.ui.composable.QuestionTitle
import com.chadbingham.quiz.ui.composable.SingleChoiceQuestion
import com.chadbingham.quiz.ui.composable.TextInputQuestion
import com.chadbingham.quiz.ui.composable.TrueFalseQuestion
import com.chadbingham.quiz.ui.theme.CricutQuizTheme
import com.chadbingham.quiz.ui.viewmodel.QuizState
import com.chadbingham.quiz.util.PreviewQuizState

@Composable
fun QuizScreen(
    modifier: Modifier,
    quizState: QuizState,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    submitAnswer: (UserAnswer) -> Unit,
    startOver: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = { quizState.currentIndex / quizState.questionsAndAnswers.size.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (quizState.currentIndex != quizState.questionsAndAnswers.size) {
                QuestionTitle(
                    modifier = Modifier.padding(bottom = 16.dp),
                    quizState.currentQuestion.text
                )
            }

            AnimatedContent(
                targetState = quizState.currentIndex,
                transitionSpec = {
                    if (targetState == quizState.questionsAndAnswers.size) {
                        slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut()
                    } else if (initialState == quizState.questionsAndAnswers.size && targetState < initialState) {
                        slideInVertically { height -> -height } + fadeIn() togetherWith
                                slideOutVertically { height -> height } + fadeOut()
                    } else if (targetState > initialState) {
                        slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()
                    } else {
                        slideInHorizontally { width -> -width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> width } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }, label = "quiz"
            ) { index ->
                if (index >= quizState.questionsAndAnswers.size) {
                    SummaryScreen() {
                        startOver()
                    }
                } else {
                    val qAnda = quizState.getQuestionAndAnswer(index)
                    val question = qAnda.first
                    val answer: UserAnswer = qAnda.second
                    when (question) {
                        is Question.TrueFalse -> {
                            TrueFalseQuestion(
                                modifier = Modifier.padding(vertical = 16.dp),
                                answer = answer as UserAnswer.TrueFalse,
                                onAnswerSelected = submitAnswer
                            )
                        }

                        is Question.SingleChoice -> {
                            SingleChoiceQuestion(
                                modifier = Modifier.padding(vertical = 16.dp),
                                question = question,
                                answer = answer as UserAnswer.SingleChoice,
                                onAnswerSelected = submitAnswer
                            )
                        }

                        is Question.MultipleChoice -> {
                            MultipleChoiceQuestion(
                                modifier = Modifier.padding(vertical = 16.dp),
                                question = question,
                                answer = answer as UserAnswer.MultipleChoice,
                                onAnswerSelected = submitAnswer
                            )
                        }

                        is Question.TextInput -> {
                            TextInputQuestion(
                                modifier = Modifier.padding(vertical = 16.dp),
                                question = question,
                                answer = answer as UserAnswer.TextInput,
                                onAnswerSelected = submitAnswer
                            )
                        }
                    }
                }
            }
        }

        if (quizState.currentIndex != quizState.questionsAndAnswers.size) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = if (quizState.currentIndex > 0)
                    Arrangement.SpaceBetween else Arrangement.End
            ) {
                if (quizState.currentIndex > 0) {
                    Button(
                        onClick = { onPrevious() },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Text(stringResource(R.string.all_previous))
                    }
                }

                Button(
                    onClick = { onNext() },
                    enabled = quizState.nextEnabled || quizState.currentQuestion.type == TRUE_FALSE
                ) {
                    Text(
                        if (quizState.currentIndex == quizState.questionsAndAnswers.size - 1) {
                            stringResource(R.string.all_submit)
                        } else {
                            stringResource(R.string.all_next)
                        }
                    )
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuizScreenPreview() {
    CricutQuizTheme {
        QuizScreen(
            Modifier, PreviewQuizState.getQuizState(3),
            onNext = { },
            onPrevious = { },
            submitAnswer = { },
            startOver = { }
        )
    }
}
