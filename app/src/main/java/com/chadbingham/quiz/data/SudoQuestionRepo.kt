/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.data

import android.content.Context
import com.chadbingham.quiz.R

/**
 * Fake repo to return awesome questions
 */
class SudoQuestionRepo(private val context: Context) {

    fun getQuestions(): List<Question> {
        return listOf(
            Question.TrueFalse(
                text = context.getString(R.string.question_one)
            ),

            Question.SingleChoice(
                text = context.getString(R.string.question_two),
                options = context.resources.getStringArray(R.array.question_two_options).toList(),
            ),

            Question.MultipleChoice(
                text = context.getString(R.string.question_three),
                options = context.resources.getStringArray(R.array.question_three_options).toList(),
            ),

            Question.TextInput(
                text = context.getString(R.string.question_four),
                hint = context.getString(R.string.answer)
            )
        )
    }
}