package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.gridlayout.widget.GridLayout

class TicTacToeActivity : AppCompatActivity() {

    private lateinit var textViewStatus: TextView
    private lateinit var gridLayoutTicTacToe: GridLayout
    private lateinit var buttonReset: Button

    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        textViewStatus = findViewById(R.id.textViewStatus)
        gridLayoutTicTacToe = findViewById(R.id.gridLayoutTicTacToe)
        buttonReset = findViewById(R.id.buttonReset)

        initializeBoard()
        updateStatusText()
    }

    private fun initializeBoard() {
        for (i in 0 until gridLayoutTicTacToe.childCount) {
            val button = gridLayoutTicTacToe.getChildAt(i) as Button
            button.text = ""
            button.isEnabled = true // Enable buttons for a new game
        }
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameActive = true
        updateStatusText()
    }

    fun onCellClick(view: View) {
        if (!gameActive || view !is Button || view.text.isNotEmpty()) {
            return // Ignore click if game is over, view is not a button, or cell is already played
        }

        val buttonIndex = gridLayoutTicTacToe.indexOfChild(view)
        val row = buttonIndex / 3
        val col = buttonIndex % 3

        if (board[row][col].isEmpty()) {
            board[row][col] = currentPlayer
            view.text = currentPlayer
            view.isEnabled = false // Disable button after it's clicked

            if (checkWinCondition(row, col)) {
                textViewStatus.text = "Player $currentPlayer wins!"
                gameActive = false
                // Optionally disable all buttons
                // disableAllCells()
            } else if (isBoardFull()) {
                textViewStatus.text = "It's a draw!"
                gameActive = false
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                updateStatusText()
            }
        }
    }

    private fun updateStatusText() {
        textViewStatus.text = "Player $currentPlayer's turn"
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

    // Optional: Call this when game ends to prevent further clicks
    /* private fun disableAllCells() {
        for (i in 0 until gridLayoutTicTacToe.childCount) {
            gridLayoutTicTacToe.getChildAt(i).isEnabled = false
        }
    }*/

    fun resetGame(view: View) {
        initializeBoard()
    }
}
