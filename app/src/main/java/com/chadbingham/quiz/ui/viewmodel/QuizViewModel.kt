/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

@file:OptIn(SavedStateHandleSaveableApi::class)

package com.chadbingham.quiz.ui.viewmodel

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import com.chadbingham.quiz.data.Question
import com.chadbingham.quiz.data.QuestionType
import com.chadbingham.quiz.data.SudoQuestionRepo
import com.chadbingham.quiz.data.UserAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

class QuizViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(),
) : AndroidViewModel(application) {

    //normally injected
    private val fakeRepo: SudoQuestionRepo = SudoQuestionRepo(application)

    private val _quizState = MutableStateFlow(
        savedStateHandle.get<QuizState>("quizState") ?: createNewQuizState()
    )
    val quizState: StateFlow<QuizState> = _quizState

    private var state: QuizState
        get() = _quizState.value
        set(value) {
            _quizState.value = if (value.currentIndex != value.questionsAndAnswers.size) {
                //need to reset nextEnabled
                value.copy(nextEnabled = value.getCurrentAnswer<UserAnswer>().isValid)
            } else {
                value
            }

            savedStateHandle["quizState"] = _quizState.value
        }

    private fun createNewQuizState(): QuizState {
        val questionsAndAnswers = linkedMapOf<Question, UserAnswer>()
        fakeRepo.getQuestions().forEach {
            questionsAndAnswers[it] = it.defaultAnswer()
        }
        return QuizState(questionsAndAnswers = questionsAndAnswers)
    }

    fun startOver() {
        state = createNewQuizState()
    }

    fun submitAnswer(answer: UserAnswer) {
        val qAndA = state.questionsAndAnswers.toMutableMap()
        qAndA[state.currentQuestion] = answer
        state = state.copy(questionsAndAnswers = qAndA, nextEnabled = answer.isValid)
    }

    fun nextQuestion() {
        if (state.nextEnabled || state.currentQuestion.type == QuestionType.TRUE_FALSE) {
            state = state.copy(currentIndex = state.currentIndex + 1)
        }
    }

    fun previousQuestion() {
        if (state.currentIndex > 0) {
            state = state.copy(currentIndex = state.currentIndex - 1)
        }
    }
}

@Parcelize
data class QuizState(
    val questionsAndAnswers: Map<Question, UserAnswer>,
    val currentIndex: Int = 0,
    val nextEnabled: Boolean = false,
) : Parcelable {

    private val currentQuestionAndAnswer: Pair<Question, UserAnswer>
        get() = getQuestionAndAnswer(currentIndex)

    val currentQuestion: Question
        get() = currentQuestionAndAnswer.first

    /**
     * This will return an answer even if there is currently not answer
     * available. It WILL NOT set a default answer if the UserAnswer is
     * missing from userAnswers
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : UserAnswer> getCurrentAnswer(): T {
        return currentQuestionAndAnswer.second as T
    }

    fun getQuestionAndAnswer(index: Int): Pair<Question, UserAnswer> {
        if (index < 0 || index >= questionsAndAnswers.size) {
            error("Not found")
        }
        return questionsAndAnswers.entries.elementAt(index).let { entry ->
            entry.key to entry.value
        }
    }
}