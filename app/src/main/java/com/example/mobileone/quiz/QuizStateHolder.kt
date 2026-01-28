package com.example.mobileone.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mobileone.data.QuizData

class QuizStateHolder(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var uiState by mutableStateOf(restoreState())
        private set
    
    private val questions = QuizData.questions

    companion object {
        private const val KEY_QUESTION_INDEX = "question_index"
        private const val KEY_SELECTED_ANSWER = "selected_answer"
        private const val KEY_CORRECT_COUNT = "correct_count"
        private const val KEY_IS_SUBMITTED = "is_submitted"
    }
    
    fun startQuiz() {
        updateState(
            index = 0,
            selectedAnswer = null,
            correctCount = 0,
            isSubmitted = false
        )
    }
    
    fun selectAnswer(answerIndex: Int) {
        if (!uiState.isAnswerSubmitted) {
            updateState(
                index = uiState.currentQuestionIndex,
                selectedAnswer = answerIndex,
                correctCount = uiState.correctAnswersCount,
                isSubmitted = false
            )
        }
    }

    fun onNextClicked() {
        if (!uiState.isAnswerSubmitted) {
            submitAnswer()
        } else {
            nextQuestion()
        }
    }
    
    private fun submitAnswer() {
        if (uiState.selectedAnswerIndex != null && !uiState.isAnswerSubmitted) {
            val currentQuestion = questions[uiState.currentQuestionIndex]
            val isCorrect = uiState.selectedAnswerIndex == currentQuestion.correctAnswerIndex
            val newCorrectCount = if (isCorrect) uiState.correctAnswersCount + 1 else uiState.correctAnswersCount
            
            updateState(
                index = uiState.currentQuestionIndex,
                selectedAnswer = uiState.selectedAnswerIndex,
                correctCount = newCorrectCount,
                isSubmitted = true
            )
        }
    }
    
    private fun nextQuestion() {
        val nextIndex = uiState.currentQuestionIndex + 1
        
        // Reset selection for next question
        updateState(
            index = nextIndex,
            selectedAnswer = null,
            correctCount = uiState.correctAnswersCount,
            isSubmitted = false
        )
    }
    
    fun restartQuiz() {
        updateState(
            index = -1, // Welcome
            selectedAnswer = null,
            correctCount = 0,
            isSubmitted = false
        )
    }

    private fun updateState(index: Int, selectedAnswer: Int?, correctCount: Int, isSubmitted: Boolean) {
        savedStateHandle[KEY_QUESTION_INDEX] = index
        savedStateHandle[KEY_SELECTED_ANSWER] = selectedAnswer ?: -1
        savedStateHandle[KEY_CORRECT_COUNT] = correctCount
        savedStateHandle[KEY_IS_SUBMITTED] = isSubmitted
        
        uiState = buildUiState(index, selectedAnswer, correctCount, isSubmitted)
    }

    private fun restoreState(): QuizUiState {
        val index = savedStateHandle.get<Int>(KEY_QUESTION_INDEX) ?: -1
        val selectedAnswer = savedStateHandle.get<Int>(KEY_SELECTED_ANSWER)?.takeIf { it != -1 }
        val correctCount = savedStateHandle.get<Int>(KEY_CORRECT_COUNT) ?: 0
        val isSubmitted = savedStateHandle.get<Boolean>(KEY_IS_SUBMITTED) ?: false
        
        return buildUiState(index, selectedAnswer, correctCount, isSubmitted)
    }

    private fun buildUiState(index: Int, selectedAnswer: Int?, correctCount: Int, isSubmitted: Boolean): QuizUiState {
        val screen = when {
            index < 0 -> QuizScreen.Welcome
            index < questions.size -> QuizScreen.Question(
                question = questions[index],
                questionNumber = index + 1
            )
            else -> QuizScreen.Result(
                correctAnswers = correctCount,
                totalQuestions = questions.size
            )
        }

        return QuizUiState(
            currentScreen = screen,
            currentQuestionIndex = index,
            selectedAnswerIndex = selectedAnswer,
            correctAnswersCount = correctCount,
            isAnswerSubmitted = isSubmitted
        )
    }
}
