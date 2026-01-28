package com.example.mobileone.data

object QuizData {
    val questions = listOf(
        Question(
            id = 1,
            text = "Что такое Jetpack Compose?",
            options = listOf(
                "Библиотека для работы с сетью",
                "Современный UI toolkit для Android",
                "База данных для Android",
                "Система сборки проектов"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 2,
            text = "Что такое State Hoisting в Compose?",
            options = listOf(
                "Поднятие состояния наверх в иерархии компонентов",
                "Сохранение состояния в базе данных",
                "Передача состояния через Intent",
                "Создание локального состояния в каждом composable"
            ),
            correctAnswerIndex = 0
        ),
        Question(
            id = 3,
            text = "Какой аннотацией помечается composable функция?",
            options = listOf(
                "@Compose",
                "@Composable",
                "@View",
                "@Component"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 4,
            text = "Что делает rememberSaveable?",
            options = listOf(
                "Сохраняет состояние при повороте экрана",
                "Сохраняет состояние в SharedPreferences",
                "Сохраняет состояние при пересоздании Activity",
                "Все вышеперечисленное"
            ),
            correctAnswerIndex = 0
        ),
        Question(
            id = 5,
            text = "Что такое State Holder?",
            options = listOf(
                "Composable функция",
                "Класс, который хранит состояние и обрабатывает события",
                "Layout компонент",
                "Анимация в Compose"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 6,
            text = "Какой модификатор используется для заполнения всего доступного пространства?",
            options = listOf(
                "fillMaxWidth()",
                "fillMaxSize()",
                "fillMaxHeight()",
                "fillParent()"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 7,
            text = "Что такое recomposition в Compose?",
            options = listOf(
                "Пересборка UI при изменении состояния",
                "Создание нового Activity",
                "Перезагрузка приложения",
                "Очистка памяти"
            ),
            correctAnswerIndex = 0
        ),
        Question(
            id = 8,
            text = "Какой компонент используется для создания списка в Compose?",
            options = listOf(
                "ListView",
                "RecyclerView",
                "LazyColumn",
                "ScrollView"
            ),
            correctAnswerIndex = 2
        ),
        Question(
            id = 9,
            text = "Что означает stateless UI?",
            options = listOf(
                "UI без состояния, только отображает данные и вызывает callbacks",
                "UI с локальным состоянием",
                "UI без анимаций",
                "UI без стилей"
            ),
            correctAnswerIndex = 0
        ),
        Question(
            id = 10,
            text = "Какой тип используется для управления состоянием в Compose?",
            options = listOf(
                "var",
                "val",
                "mutableStateOf",
                "const"
            ),
            correctAnswerIndex = 2
        )
    )
    
    const val QUIZ_TITLE = "Kotlin Basics Quiz"
    const val QUIZ_DESCRIPTION = "Проверьте свои знания основ Kotlin и Jetpack Compose!"
}
