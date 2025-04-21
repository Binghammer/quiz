/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.util

import com.chadbingham.quiz.data.Question
import com.chadbingham.quiz.data.UserAnswer
import com.chadbingham.quiz.ui.viewmodel.QuizState

object PreviewQuizState {

    fun getQuizState(currentIndex: Int = 0): QuizState {
        val questionsAndAnswers = linkedMapOf<Question, UserAnswer>()
        getQuestions().forEach {
            questionsAndAnswers[it] = it.defaultAnswer()
        }
        val index = if (currentIndex > questionsAndAnswers.size) {
            questionsAndAnswers.size //will be summary page
        } else {
            currentIndex
        }
        return QuizState(
            questionsAndAnswers = questionsAndAnswers, currentIndex = index
        )
    }

    private fun getQuestions(): List<Question> {
        return listOf(
            getTrueFalseQuestion(),
            getSingleChoiceQuestion(),
            getMultiChoiceQuestion(),
            getUserInputQuestion()
        )
    }

    fun getTrueFalseQuestion(): Question.TrueFalse {
        return Question.TrueFalse(
            text = "Is Kotlin awesome?",
        )
    }

    fun getSingleChoiceQuestion(): Question.SingleChoice {
        return Question.SingleChoice(
            text = "What is the capital of France?",
            options = listOf("Berlin", "Madrid", "Paris", "Rome"),
        )
    }

    fun getMultiChoiceQuestion(): Question.MultipleChoice {
        return Question.MultipleChoice(
            text = "Select prime numbers",
            options = listOf("2", "4", "5", "9"),
        )
    }

    fun getUserInputQuestion(): Question.TextInput {
        return Question.TextInput(
            text = "What is the name of your pet?",
            hint = "Name",
        )
    }
}