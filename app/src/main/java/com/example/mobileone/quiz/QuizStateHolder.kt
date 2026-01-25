package com.example.mobileone.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mobileone.data.QuizData

class QuizStateHolder {
    var uiState by mutableStateOf(QuizUiState())
        private set
    
    private val questions = QuizData.questions
    
    fun startQuiz() {
        uiState = QuizUiState(
            currentScreen = QuizScreen.Question(
                question = questions[0],
                questionNumber = 1
            ),
            currentQuestionIndex = 0
        )
    }
    
    fun selectAnswer(answerIndex: Int) {
        if (!uiState.isAnswerSubmitted) {
            uiState = uiState.copy(selectedAnswerIndex = answerIndex)
        }
    }
    
    fun submitAnswer() {
        if (uiState.selectedAnswerIndex != null && !uiState.isAnswerSubmitted) {
            val currentQuestion = questions[uiState.currentQuestionIndex]
            val isCorrect = uiState.selectedAnswerIndex == currentQuestion.correctAnswerIndex
            val newCorrectCount = if (isCorrect) uiState.correctAnswersCount + 1 else uiState.correctAnswersCount
            
            uiState = uiState.copy(
                isAnswerSubmitted = true,
                correctAnswersCount = newCorrectCount
            )
        }
    }
    
    fun nextQuestion() {
        val nextIndex = uiState.currentQuestionIndex + 1
        
        if (nextIndex < questions.size) {
            uiState = QuizUiState(
                currentScreen = QuizScreen.Question(
                    question = questions[nextIndex],
                    questionNumber = nextIndex + 1
                ),
                currentQuestionIndex = nextIndex,
                correctAnswersCount = uiState.correctAnswersCount
            )
        } else {
            // Переход к экрану результата
            uiState = QuizUiState(
                currentScreen = QuizScreen.Result(
                    correctAnswers = uiState.correctAnswersCount,
                    totalQuestions = questions.size
                ),
                currentQuestionIndex = nextIndex,
                correctAnswersCount = uiState.correctAnswersCount
            )
        }
    }
    
    fun restartQuiz() {
        uiState = QuizUiState()
    }
}
