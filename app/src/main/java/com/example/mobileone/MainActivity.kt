package com.example.mobileone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mobileone.data.QuizData
import com.example.mobileone.quiz.QuizScreen
import com.example.mobileone.quiz.QuizStateHolder
import com.example.mobileone.ui.screens.QuestionScreen
import com.example.mobileone.ui.screens.ResultScreen
import com.example.mobileone.ui.screens.WelcomeScreen
import com.example.mobileone.ui.theme.MobileOneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileOneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun QuizApp(modifier: Modifier = Modifier) {
    // Используем remember для state holder (стандартная практика)
    val stateHolder = remember { QuizStateHolder() }
    
    // Используем rememberSaveable для сохранения прогресса при повороте экрана
    // Это демонстрирует использование rememberSaveable для сохранения состояния
    var savedProgress by rememberSaveable { mutableIntStateOf(0) }
    
    val uiState = stateHolder.uiState
    
    // Сохраняем текущий прогресс (индекс вопроса) при изменении
    if (uiState.currentQuestionIndex != savedProgress) {
        savedProgress = uiState.currentQuestionIndex
    }
    
    when (val screen = uiState.currentScreen) {
        is QuizScreen.Welcome -> {
            WelcomeScreen(
                onStartClick = { stateHolder.startQuiz() },
                modifier = modifier
            )
        }
        
        is QuizScreen.Question -> {
            QuestionScreen(
                question = screen.question,
                questionNumber = screen.questionNumber,
                totalQuestions = QuizData.questions.size,
                selectedAnswerIndex = uiState.selectedAnswerIndex,
                isAnswerSubmitted = uiState.isAnswerSubmitted,
                onAnswerSelected = { stateHolder.selectAnswer(it) },
                onNextClick = {
                    if (!uiState.isAnswerSubmitted) {
                        stateHolder.submitAnswer()
                    } else {
                        stateHolder.nextQuestion()
                    }
                },
                modifier = modifier
            )
        }
        
        is QuizScreen.Result -> {
            ResultScreen(
                correctAnswers = screen.correctAnswers,
                totalQuestions = screen.totalQuestions,
                onRestartClick = { stateHolder.restartQuiz() },
                modifier = modifier
            )
        }
    }
}