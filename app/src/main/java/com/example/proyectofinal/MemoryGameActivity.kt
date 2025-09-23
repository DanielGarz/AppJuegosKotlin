package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Button
import android.widget.GridLayout
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MemoryGameActivity : AppCompatActivity() {

    private lateinit var gridLayoutMemory: GridLayout
    private lateinit var textViewMoves: TextView
    private lateinit var textViewTime: TextView
    private lateinit var textViewBestScore: TextView
    private lateinit var buttonNewGame: Button
    private lateinit var sharedPreferences: SharedPreferences

    private val gameSize = 4 // 4x4 grid
    private val totalCards = gameSize * gameSize
    private val cards = mutableListOf<MemoryCard>()
    private val cardButtons = mutableListOf<Button>()
    private var flippedCards = mutableListOf<Int>()
    private var matchedPairs = 0
    private var moves = 0
    private var gameStartTime = 0L
    private var isGameActive = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var timeRunnable: Runnable

    // Emojis para las cartas
    private val cardEmojis = listOf(
        "🎮", "🕹️", "🎯", "🎲", "🃏", "🎪", "🎨", "🎭"
    )

    data class MemoryCard(
        val id: Int,
        val emoji: String,
        var isFlipped: Boolean = false,
        var isMatched: Boolean = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        initializeViews()
        sharedPreferences = getSharedPreferences("MemoryGameStats", Context.MODE_PRIVATE)

        loadBestScore()
        setupNewGame()
    }

    private fun initializeViews() {
        gridLayoutMemory = findViewById(R.id.gridLayoutMemory)
        textViewMoves = findViewById(R.id.textViewMoves)
        textViewTime = findViewById(R.id.textViewTime)
        textViewBestScore = findViewById(R.id.textViewBestScore)
        buttonNewGame = findViewById(R.id.buttonNewGameMemory)

        buttonNewGame.setOnClickListener {
            setupNewGame()
        }

        findViewById<Button>(R.id.buttonBackToMenuMemory).setOnClickListener {
            finish()
        }
    }

    private fun loadBestScore() {
        val bestScore = sharedPreferences.getInt("BestScore", 0)
        textViewBestScore.text = if (bestScore > 0) {
            getString(R.string.best_score, bestScore)
        } else {
            "Mejor puntuación: --"
        }
    }

    private fun setupNewGame() {
        // Reset game state
        moves = 0
        matchedPairs = 0
        flippedCards.clear()
        isGameActive = true
        gameStartTime = System.currentTimeMillis()

        // Create cards with pairs
        cards.clear()
        val selectedEmojis = cardEmojis.take(totalCards / 2)
        val gameEmojis = mutableListOf<String>()

        // Add each emoji twice to create pairs
        selectedEmojis.forEach { emoji ->
            gameEmojis.add(emoji)
            gameEmojis.add(emoji)
        }

        // Shuffle the emojis
        gameEmojis.shuffle()

        // Create card objects
        gameEmojis.forEachIndexed { index, emoji ->
            cards.add(MemoryCard(index, emoji))
        }

        // Setup UI
        setupGrid()
        updateUI()
        startTimer()
    }

    private fun setupGrid() {
        gridLayoutMemory.removeAllViews()
        cardButtons.clear()

        for (i in 0 until totalCards) {
            val button = Button(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = dpToPx(70)
                    height = dpToPx(70)
                    setMargins(4, 4, 4, 4)
                }

                textSize = 24f
                text = "?"
                setBackgroundColor(getColor(R.color.light_gray))
                setOnClickListener { onCardClick(i) }
            }

            cardButtons.add(button)
            gridLayoutMemory.addView(button)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun onCardClick(cardIndex: Int) {
        if (!isGameActive) return

        val card = cards[cardIndex]
        if (card.isFlipped || card.isMatched) return
        if (flippedCards.size >= 2) return

        // Flip the card
        flipCard(cardIndex, true)
        flippedCards.add(cardIndex)

        if (flippedCards.size == 2) {
            moves++
            updateUI()

            // Check for match after a short delay
            handler.postDelayed({
                checkForMatch()
            }, 1000)
        }
    }

    private fun flipCard(cardIndex: Int, show: Boolean) {
        val card = cards[cardIndex]
        val button = cardButtons[cardIndex]

        // Animation
        val scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0f)
        scaleX.duration = 150

        scaleX.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: android.animation.Animator) {}
            override fun onAnimationEnd(animation: android.animation.Animator) {
                button.text = if (show) card.emoji else "?"
                button.setBackgroundColor(
                    getColor(if (show) R.color.white else R.color.light_gray)
                )

                val scaleBackX = ObjectAnimator.ofFloat(button, "scaleX", 0f, 1f)
                scaleBackX.duration = 150
                scaleBackX.start()
            }
            override fun onAnimationCancel(animation: android.animation.Animator) {}
            override fun onAnimationRepeat(animation: android.animation.Animator) {}
        })

        scaleX.start()
        card.isFlipped = show
    }

    private fun checkForMatch() {
        if (flippedCards.size != 2) return

        val card1 = cards[flippedCards[0]]
        val card2 = cards[flippedCards[1]]

        if (card1.emoji == card2.emoji) {
            // Match found!
            card1.isMatched = true
            card2.isMatched = true

            // Highlight matched cards
            cardButtons[flippedCards[0]].setBackgroundColor(
                getColor(R.color.primary_red_light)
            )
            cardButtons[flippedCards[1]].setBackgroundColor(
                getColor(R.color.primary_red_light)
            )

            matchedPairs++

            if (matchedPairs == totalCards / 2) {
                // Game won!
                isGameActive = false
                handler.removeCallbacks(timeRunnable)
                showWinDialog()
            }
        } else {
            // No match, flip cards back
            flipCard(flippedCards[0], false)
            flipCard(flippedCards[1], false)
        }

        flippedCards.clear()
    }

    private fun updateUI() {
        textViewMoves.text = getString(R.string.moves_counter, moves)
    }

    private fun startTimer() {
        timeRunnable = object : Runnable {
            override fun run() {
                if (isGameActive) {
                    val elapsedTime = (System.currentTimeMillis() - gameStartTime) / 1000
                    val minutes = elapsedTime / 60
                    val seconds = elapsedTime % 60
                    textViewTime.text = getString(R.string.time_counter, String.format("%02d:%02d", minutes, seconds))
                    handler.postDelayed(this, 1000)
                }
            }
        }
        handler.post(timeRunnable)
    }

    private fun showWinDialog() {
        val elapsedTime = (System.currentTimeMillis() - gameStartTime) / 1000
        val minutes = elapsedTime / 60
        val seconds = elapsedTime % 60
        val timeString = String.format("%02d:%02d", minutes, seconds)

        // Check if this is a new best score
        val currentBestScore = sharedPreferences.getInt("BestScore", Int.MAX_VALUE)
        if (moves < currentBestScore) {
            sharedPreferences.edit().putInt("BestScore", moves).apply()
            loadBestScore()
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.memory_game_won, moves, timeString))
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                setupNewGame()
            }
            .setNegativeButton(getString(R.string.back_to_menu)) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timeRunnable)
    }
}
