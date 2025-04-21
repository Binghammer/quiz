/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.data

import android.os.Parcelable
import com.chadbingham.quiz.data.QuestionType.MULTI_CHOICE
import com.chadbingham.quiz.data.QuestionType.SINGLE_CHOICE
import com.chadbingham.quiz.data.QuestionType.TEXT_INPUT
import com.chadbingham.quiz.data.QuestionType.TRUE_FALSE
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class UserAnswer : Parcelable {
    abstract val type: QuestionType
    abstract val isValid: Boolean

    @Parcelize
    data class TrueFalse(val answer: Boolean = false) : UserAnswer() {
        @IgnoredOnParcel
        override val type: QuestionType = TRUE_FALSE
        override val isValid: Boolean
            get() = answer
    }

    @Parcelize
    data class SingleChoice(val index: Int = -1) : UserAnswer() {
        @IgnoredOnParcel
        override val type: QuestionType = SINGLE_CHOICE
        override val isValid: Boolean
            get() = index != -1
    }

    @Parcelize
    data class MultipleChoice(val indices: Set<Int> = emptySet()) : UserAnswer() {
        @IgnoredOnParcel
        override val type: QuestionType = MULTI_CHOICE
        override val isValid: Boolean
            get() = indices.isNotEmpty()
    }

    @Parcelize
    data class TextInput(val text: String = "") : UserAnswer() {
        @IgnoredOnParcel
        override val type: QuestionType = TEXT_INPUT
        override val isValid: Boolean
            get() = text.isNotBlank()
    }
}