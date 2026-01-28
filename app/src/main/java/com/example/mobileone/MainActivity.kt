package com.example.mobileone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
    // Используем viewModel() для получения экземпляра StateHolder, который переживет повороты экрана
    val stateHolder: QuizStateHolder = viewModel()
    
    val uiState = stateHolder.uiState
    
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
                onNextClick = { stateHolder.onNextClicked() },
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