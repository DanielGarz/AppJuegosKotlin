package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.gridlayout.widget.GridLayout
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.content.SharedPreferences
import android.content.Context
import androidx.appcompat.app.AlertDialog

class TicTacToeActivity : AppCompatActivity() {

    private lateinit var textViewStatus: TextView
    private lateinit var gridLayoutTicTacToe: GridLayout
    private lateinit var buttonReset: Button
    private lateinit var textViewScore: TextView
    private lateinit var sharedPreferences: SharedPreferences

    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } }
    private var gameActive = true
    private var playerXWins = 0
    private var playerOWins = 0
    private var draws = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        textViewStatus = findViewById(R.id.textViewStatus)
        gridLayoutTicTacToe = findViewById(R.id.gridLayoutTicTacToe)
        buttonReset = findViewById(R.id.buttonReset)
        textViewScore = findViewById(R.id.textViewScore)

        sharedPreferences = getSharedPreferences("TicTacToeStats", Context.MODE_PRIVATE)
        loadStats()

        // Setup button listeners
        buttonReset.setOnClickListener { initializeBoard() }
        findViewById<Button>(R.id.buttonResetStats).setOnClickListener { showResetStatsDialog() }
        findViewById<Button>(R.id.buttonBackToMenu).setOnClickListener { finish() }

        initializeBoard()
        updateStatusText()
        updateScoreDisplay()
    }

    private fun loadStats() {
        playerXWins = sharedPreferences.getInt("PlayerXWins", 0)
        playerOWins = sharedPreferences.getInt("PlayerOWins", 0)
        draws = sharedPreferences.getInt("Draws", 0)
    }

    private fun saveStats() {
        val editor = sharedPreferences.edit()
        editor.putInt("PlayerXWins", playerXWins)
        editor.putInt("PlayerOWins", playerOWins)
        editor.putInt("Draws", draws)
        editor.apply()
    }

    private fun updateScoreDisplay() {
        textViewScore.text = getString(R.string.score_format, playerXWins, playerOWins, draws)
    }

    private fun initializeBoard() {
        for (i in 0 until gridLayoutTicTacToe.childCount) {
            val button = gridLayoutTicTacToe.getChildAt(i) as Button
            button.text = ""
            button.isEnabled = true
            button.alpha = 1.0f
            button.scaleX = 1.0f
            button.scaleY = 1.0f
        }
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameActive = true
        updateStatusText()
    }

    fun onCellClick(view: View) {
        if (!gameActive || view !is Button || view.text.isNotEmpty()) {
            return
        }

        val buttonIndex = gridLayoutTicTacToe.indexOfChild(view)
        val row = buttonIndex / 3
        val col = buttonIndex % 3

        if (board[row][col].isEmpty()) {
            // Animate button press
            animateButtonPress(view)

            board[row][col] = currentPlayer
            view.text = currentPlayer
            view.isEnabled = false

            if (checkWinCondition(row, col)) {
                highlightWinningCells(getWinningCells(row, col))
                textViewStatus.text = getString(R.string.player_wins, currentPlayer)
                gameActive = false

                // Update stats
                if (currentPlayer == "X") playerXWins++ else playerOWins++
                saveStats()
                updateScoreDisplay()

                showGameEndDialog(getString(R.string.player_wins, currentPlayer))
            } else if (isBoardFull()) {
                textViewStatus.text = getString(R.string.its_a_draw)
                gameActive = false
                draws++
                saveStats()
                updateScoreDisplay()
                showGameEndDialog(getString(R.string.its_a_draw))
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                updateStatusText()
            }
        }
    }

    private fun animateButtonPress(button: Button) {
        val scaleDown = ObjectAnimator.ofFloat(button, "scaleX", 1.0f, 0.8f)
        val scaleDownY = ObjectAnimator.ofFloat(button, "scaleY", 1.0f, 0.8f)
        val scaleUp = ObjectAnimator.ofFloat(button, "scaleX", 0.8f, 1.0f)
        val scaleUpY = ObjectAnimator.ofFloat(button, "scaleY", 0.8f, 1.0f)

        val animatorSet = AnimatorSet()
        animatorSet.play(scaleDown).with(scaleDownY)
        animatorSet.play(scaleUp).with(scaleUpY).after(scaleDown)
        animatorSet.duration = 100
        animatorSet.start()
    }

    private fun highlightWinningCells(winningCells: List<Int>) {
        for (cellIndex in winningCells) {
            val button = gridLayoutTicTacToe.getChildAt(cellIndex) as Button
            val fadeOut = ObjectAnimator.ofFloat(button, "alpha", 1.0f, 0.3f)
            val fadeIn = ObjectAnimator.ofFloat(button, "alpha", 0.3f, 1.0f)

            val animatorSet = AnimatorSet()
            animatorSet.play(fadeOut).before(fadeIn)
            animatorSet.duration = 300
            animatorSet.start()
        }
    }

    private fun getWinningCells(row: Int, col: Int): List<Int> {
        val winningCells = mutableListOf<Int>()

        // Check row
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) {
            winningCells.addAll(listOf(row * 3, row * 3 + 1, row * 3 + 2))
        }
        // Check column
        else if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) {
            winningCells.addAll(listOf(col, col + 3, col + 6))
        }
        // Check main diagonal
        else if (row == col && board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            winningCells.addAll(listOf(0, 4, 8))
        }
        // Check anti-diagonal
        else if (row + col == 2 && board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            winningCells.addAll(listOf(2, 4, 6))
        }

        return winningCells
    }

    private fun updateStatusText() {
        textViewStatus.text = getString(R.string.player_turn, currentPlayer)
    }

    private fun checkWinCondition(row: Int, col: Int): Boolean {
        // Check row
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) return true
        // Check column
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) return true
        // Check diagonals
        if (row == col && board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (row + col == 2 && board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j].isEmpty()) {
                    return false
                }
            }
        }
        return true
    }

    private fun showGameEndDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.game_over))
            .setMessage(message)
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                initializeBoard()
            }
            .setNegativeButton(getString(R.string.back_to_menu)) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun showResetStatsDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.reset_stats))
            .setMessage(getString(R.string.reset_stats_confirmation))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                playerXWins = 0
                playerOWins = 0
                draws = 0
                saveStats()
                updateScoreDisplay()
                Toast.makeText(this, getString(R.string.stats_reset), Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }
}
