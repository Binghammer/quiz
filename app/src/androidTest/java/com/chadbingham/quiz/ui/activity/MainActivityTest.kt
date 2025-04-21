/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.ui.activity

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chadbingham.quiz.ui.screen.QuizScreen
import com.chadbingham.quiz.ui.theme.CricutQuizTheme
import com.chadbingham.quiz.ui.viewmodel.QuizState
import com.chadbingham.quiz.ui.viewmodel.QuizViewModel
import com.chadbingham.quiz.util.PreviewQuizState
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: QuizViewModel = mockk(relaxed = true)

    private fun setup(
        quizState: QuizState,
        onNext: () -> Unit = {},
        onPrevious: () -> Unit = {},
        startOver: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            CricutQuizTheme {
                QuizScreen(
                    modifier = Modifier,
                    quizState = quizState,
                    onNext = onNext,
                    onPrevious = onPrevious,
                    submitAnswer = { },
                    startOver = startOver,
                )
            }
        }
    }

    @Test
    fun displaysQuizScreenWithInitialState() {
        val stateFlow = MutableStateFlow(PreviewQuizState.getQuizState(0))
        every { viewModel.quizState } returns stateFlow

        setup(stateFlow.value)
        composeTestRule
            .onNodeWithText(stateFlow.value.getQuestionAndAnswer(0).first.text)
            .assertIsDisplayed()
    }

    @Test
    fun callsNextQuestionWhenNextButtonClicked() {
        val stateFlow = MutableStateFlow(PreviewQuizState.getQuizState(0))
        every { viewModel.quizState } returns stateFlow

        setup(stateFlow.value, onNext = viewModel::nextQuestion)

        // Simulate button click and verify nextQuestion is called
        composeTestRule
            .onNodeWithText("Next")
            .performClick()
        verify { viewModel.nextQuestion() }
        confirmVerified(viewModel)
    }

    @Test
    fun callsPreviousQuestionWhenPreviousButtonClicked() {
        val stateFlow = MutableStateFlow(PreviewQuizState.getQuizState(1))
        every { viewModel.quizState } returns stateFlow

        setup(stateFlow.value, onPrevious = viewModel::previousQuestion)

        // Simulate button click and verify previousQuestion is called
        composeTestRule
            .onNodeWithText("Previous")
            .performClick()
        verify { viewModel.previousQuestion() }
        confirmVerified(viewModel)
    }

    @Test
    fun nextIsEnabledWhenTrueFalse() {
        val stateFlow = MutableStateFlow(PreviewQuizState.getQuizState(0))
        every { viewModel.quizState } returns stateFlow
        setup(stateFlow.value)
        composeTestRule.onNodeWithText("Next").assertIsEnabled()
    }

    @Test
    fun previousIsGoneOnFirstQuestion() {
        val stateFlow = MutableStateFlow(PreviewQuizState.getQuizState(0))
        every { viewModel.quizState } returns stateFlow
        setup(stateFlow.value)
        composeTestRule.onNodeWithText("Previous").assertDoesNotExist()
    }

    @Test
    fun showSubmitWhenNoMoreQuestions() {
        val stateFlow = MutableStateFlow(PreviewQuizState.getQuizState(Int.MAX_VALUE))
        stateFlow.value =
            stateFlow.value.copy(currentIndex = stateFlow.value.questionsAndAnswers.size - 1)
        every { viewModel.quizState } returns stateFlow
        setup(stateFlow.value, startOver = viewModel::startOver)

        // Simulate button click and verify startOver is called
        composeTestRule.onNodeWithText("Submit").assertExists()
    }

    @Test
    fun callsStartOverWhenStartOverButtonClicked() {
        val stateFlow = MutableStateFlow(PreviewQuizState.getQuizState(Int.MAX_VALUE))
        every { viewModel.quizState } returns stateFlow
        setup(stateFlow.value, startOver = viewModel::startOver)

        // Simulate button click and verify startOver is called
        composeTestRule.onNodeWithText("Start Over").performClick()
        verify { viewModel.startOver() }
        confirmVerified(viewModel)
    }
}
