/*
 * Created by bingham
 * Copyright (c) 2025. All rights reserved.
 * May this code run smoothly and may the bugs be someone else’s problem.
 */

package com.chadbingham.quiz.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.chadbingham.quiz.ui.screen.QuizScreen
import com.chadbingham.quiz.ui.theme.CricutQuizTheme
import com.chadbingham.quiz.ui.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val quizState by viewModel.quizState.collectAsState()
            CricutQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizScreen(
                        modifier = Modifier.padding(innerPadding),
                        quizState = quizState,
                        viewModel::nextQuestion,
                        viewModel::previousQuestion,
                        viewModel::submitAnswer,
                        viewModel::startOver
                    )
                }
            }
        }
    }
}
