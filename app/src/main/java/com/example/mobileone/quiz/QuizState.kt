package com.example.mobileone.quiz

import com.example.mobileone.data.Question

sealed class QuizScreen {
    object Welcome : QuizScreen()
    data class Question(val question: com.example.mobileone.data.Question, val questionNumber: Int) : QuizScreen()
    data class Result(val correctAnswers: Int, val totalQuestions: Int) : QuizScreen()
}

data class QuizUiState(
    val currentScreen: QuizScreen = QuizScreen.Welcome,
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val correctAnswersCount: Int = 0,
    val isAnswerSubmitted: Boolean = false
)
