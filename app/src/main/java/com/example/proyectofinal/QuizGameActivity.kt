package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.LinearLayout

class QuizGameActivity : AppCompatActivity() {

    private lateinit var textViewQuestion: TextView
    private lateinit var textViewQuestionCounter: TextView
    private lateinit var textViewScore: TextView
    private lateinit var radioGroupAnswers: RadioGroup
    private lateinit var buttonNext: Button
    private lateinit var buttonNewQuiz: Button
    private lateinit var sharedPreferences: SharedPreferences

    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedAnswerIndex = -1
    private val questions = mutableListOf<QuizQuestion>()

    data class QuizQuestion(
        val question: String,
        val options: List<String>,
        val correctAnswerIndex: Int,
        val category: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_game)

        initializeViews()
        sharedPreferences = getSharedPreferences("QuizGameStats", Context.MODE_PRIVATE)

        setupQuestions()
        startNewQuiz()
    }

    private fun initializeViews() {
        textViewQuestion = findViewById(R.id.textViewQuestion)
        textViewQuestionCounter = findViewById(R.id.textViewQuestionCounter)
        textViewScore = findViewById(R.id.textViewScore)
        radioGroupAnswers = findViewById(R.id.radioGroupAnswers)
        buttonNext = findViewById(R.id.buttonNext)
        buttonNewQuiz = findViewById(R.id.buttonNewQuiz)

        buttonNext.setOnClickListener {
            handleNextButtonClick()
        }

        buttonNewQuiz.setOnClickListener {
            startNewQuiz()
        }

        findViewById<Button>(R.id.buttonBackToMenuQuiz).setOnClickListener {
            finish()
        }

        radioGroupAnswers.setOnCheckedChangeListener { _, checkedId ->
            selectedAnswerIndex = radioGroupAnswers.indexOfChild(findViewById(checkedId))
            buttonNext.isEnabled = selectedAnswerIndex != -1
        }
    }

    private fun setupQuestions() {
        questions.clear()

        // Preguntas sobre tecnología y programación
        questions.addAll(listOf(
            QuizQuestion(
                "¿Qué significa HTML?",
                listOf("Home Tool Markup Language", "Hyperlinks and Text Markup Language", "HyperText Markup Language", "Hyperlinking Text Mark Language"),
                2,
                "Tecnología"
            ),
            QuizQuestion(
                "¿Cuál es el lenguaje de programación más usado para desarrollo Android nativo?",
                listOf("Java", "Python", "JavaScript", "Kotlin"),
                3,
                "Programación"
            ),
            QuizQuestion(
                "¿Qué significa CPU?",
                listOf("Computer Personal Unit", "Central Processing Unit", "Computer Processing Unit", "Central Personal Unit"),
                1,
                "Hardware"
            ),
            QuizQuestion(
                "¿En qué año fue lanzado el primer iPhone?",
                listOf("2006", "2007", "2008", "2009"),
                1,
                "Tecnología"
            ),
            QuizQuestion(
                "¿Cuál de estos es un sistema de control de versiones?",
                listOf("GitHub", "Git", "GitLab", "Todas las anteriores"),
                3,
                "Programación"
            ),
            QuizQuestion(
                "¿Qué significa API?",
                listOf("Application Programming Interface", "Advanced Programming Interface", "Application Process Interface", "Advanced Process Interface"),
                0,
                "Programación"
            ),
            QuizQuestion(
                "¿Cuál es la resolución de pantalla 4K?",
                listOf("3840x2160", "2560x1440", "1920x1080", "4096x2160"),
                0,
                "Tecnología"
            ),
            QuizQuestion(
                "¿Qué empresa desarrolló el lenguaje de programación Swift?",
                listOf("Google", "Microsoft", "Apple", "Meta"),
                2,
                "Programación"
            ),
            QuizQuestion(
                "¿Cuál de estos NO es un sistema operativo móvil?",
                listOf("Android", "iOS", "Windows Phone", "Ubuntu Desktop"),
                3,
                "Tecnología"
            ),
            QuizQuestion(
                "¿Qué protocolo se usa principalmente para navegación web segura?",
                listOf("HTTP", "HTTPS", "FTP", "SMTP"),
                1,
                "Tecnología"
            )
        ))

        // Mezclar las preguntas
        questions.shuffle()
    }

    private fun startNewQuiz() {
        currentQuestionIndex = 0
        score = 0
        selectedAnswerIndex = -1

        setupQuestions()
        displayQuestion()
        updateUI()

        buttonNext.text = getString(R.string.next_question)
        buttonNext.isEnabled = false
    }

    private fun displayQuestion() {
        if (currentQuestionIndex >= questions.size) {
            showResults()
            return
        }

        val question = questions[currentQuestionIndex]

        textViewQuestion.text = question.question
        textViewQuestionCounter.text = getString(R.string.question_counter, currentQuestionIndex + 1, questions.size)

        // Clear previous answers
        radioGroupAnswers.removeAllViews()

        // Add answer options
        question.options.forEachIndexed { index, option ->
            val radioButton = RadioButton(this).apply {
                text = option
                id = index
                textSize = 16f
                setPadding(16, 16, 16, 16)
                setTextColor(resources.getColor(R.color.dark_gray, null))
            }
            radioGroupAnswers.addView(radioButton)
        }

        radioGroupAnswers.clearCheck()
        selectedAnswerIndex = -1
        buttonNext.isEnabled = false
    }

    private fun handleNextButtonClick() {
        if (selectedAnswerIndex == -1) return

        val currentQuestion = questions[currentQuestionIndex]

        // Check if answer is correct
        if (selectedAnswerIndex == currentQuestion.correctAnswerIndex) {
            score++
        }

        // Highlight correct and wrong answers
        highlightAnswers(currentQuestion.correctAnswerIndex, selectedAnswerIndex)

        // Move to next question or finish quiz
        currentQuestionIndex++

        if (currentQuestionIndex >= questions.size) {
            buttonNext.text = getString(R.string.finish_quiz)
            buttonNext.setOnClickListener { showResults() }
        } else {
            // Delay before showing next question
            buttonNext.postDelayed({
                displayQuestion()
                updateUI()
                buttonNext.setOnClickListener { handleNextButtonClick() }
            }, 1500)
        }

        updateUI()
    }

    private fun highlightAnswers(correctIndex: Int, selectedIndex: Int) {
        for (i in 0 until radioGroupAnswers.childCount) {
            val radioButton = radioGroupAnswers.getChildAt(i) as RadioButton
            when (i) {
                correctIndex -> {
                    radioButton.setTextColor(resources.getColor(R.color.primary_red, null))
                    radioButton.setBackgroundColor(resources.getColor(R.color.primary_red_light, null))
                }
                selectedIndex -> {
                    if (selectedIndex != correctIndex) {
                        radioButton.setTextColor(resources.getColor(R.color.error, null))
                        radioButton.setBackgroundColor(resources.getColor(R.color.error, null))
                    }
                }
                else -> {
                    radioButton.setTextColor(resources.getColor(R.color.light_gray, null))
                }
            }
            radioButton.isEnabled = false
        }
    }

    private fun updateUI() {
        textViewScore.text = getString(R.string.score_counter, score, questions.size)
    }

    private fun showResults() {
        val percentage = if (questions.isNotEmpty()) (score * 100) / questions.size else 0

        val performanceMessage = when {
            percentage >= 90 -> getString(R.string.excellent_score)
            percentage >= 70 -> getString(R.string.good_score)
            percentage >= 50 -> getString(R.string.average_score)
            else -> getString(R.string.needs_improvement)
        }

        // Save best score
        val currentBestScore = sharedPreferences.getInt("BestScore", 0)
        if (score > currentBestScore) {
            sharedPreferences.edit().putInt("BestScore", score).apply()
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.quiz_completed, score, questions.size, percentage) + "\n\n$performanceMessage")
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                startNewQuiz()
            }
            .setNegativeButton(getString(R.string.back_to_menu)) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}
