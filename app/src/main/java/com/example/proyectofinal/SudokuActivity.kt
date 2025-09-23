package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.widget.TextView
import kotlin.random.Random

class SudokuActivity : AppCompatActivity() {

    private lateinit var gridLayoutSudoku: GridLayout
    private lateinit var buttonNewGame: Button
    private lateinit var buttonCheck: Button
    private lateinit var buttonHint: Button
    private lateinit var textViewDifficulty: TextView
    private val sudokuCells = Array(9) { Array(9) { null as EditText? } }
    private val sudokuSolution = Array(9) { IntArray(9) }
    private val sudokuPuzzle = Array(9) { IntArray(9) }
    private var hintsUsed = 0
    private val maxHints = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)

        gridLayoutSudoku = findViewById(R.id.gridLayoutSudoku)
        buttonNewGame = findViewById(R.id.buttonNewGameSudoku)
        buttonCheck = findViewById(R.id.buttonCheckSudoku)
        buttonHint = findViewById(R.id.buttonHintSudoku)
        textViewDifficulty = findViewById(R.id.textViewDifficulty)

        setupSudokuGrid()
        generateNewSudoku()

        buttonNewGame.setOnClickListener {
            generateNewSudoku()
        }

        buttonCheck.setOnClickListener {
            checkSudokuSolution()
        }

        buttonHint.setOnClickListener {
            provideHint()
        }

        findViewById<Button>(R.id.buttonBackToMenuSudoku).setOnClickListener {
            finish()
        }
    }

    private fun setupSudokuGrid() {
        gridLayoutSudoku.removeAllViews()

        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val editText = EditText(this).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = dpToPx(40)
                        height = dpToPx(40)
                        setMargins(2, 2, 2, 2)
                    }

                    // Styling
                    textSize = 16f
                    gravity = android.view.Gravity.CENTER
                    maxLines = 1
                    filters = arrayOf(android.text.InputFilter.LengthFilter(1))
                    inputType = android.text.InputType.TYPE_CLASS_NUMBER

                    // Color coding for 3x3 blocks
                    val blockColor = if ((row / 3 + col / 3) % 2 == 0) {
                        Color.parseColor("#FAFAFA")
                    } else {
                        Color.parseColor("#F0F0F0")
                    }
                    setBackgroundColor(blockColor)

                    // Add text watcher for validation
                    addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                        override fun afterTextChanged(s: Editable?) {
                            val text = s.toString()
                            if (text.isNotEmpty()) {
                                val num = text.toIntOrNull()
                                if (num == null || num < 1 || num > 9) {
                                    setText("")
                                } else {
                                    validateCell(row, col, num)
                                }
                            }
                        }
                    })
                }

                sudokuCells[row][col] = editText
                gridLayoutSudoku.addView(editText)
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun validateCell(row: Int, col: Int, value: Int) {
        val cell = sudokuCells[row][col]

        // Check if the value is valid in this position
        var isValid = true

        // Check row
        for (c in 0 until 9) {
            if (c != col && sudokuCells[row][c]?.text.toString() == value.toString()) {
                isValid = false
                break
            }
        }

        // Check column
        if (isValid) {
            for (r in 0 until 9) {
                if (r != row && sudokuCells[r][col]?.text.toString() == value.toString()) {
                    isValid = false
                    break
                }
            }
        }

        // Check 3x3 box
        if (isValid) {
            val boxStartRow = (row / 3) * 3
            val boxStartCol = (col / 3) * 3

            for (r in boxStartRow until boxStartRow + 3) {
                for (c in boxStartCol until boxStartCol + 3) {
                    if ((r != row || c != col) &&
                        sudokuCells[r][c]?.text.toString() == value.toString()) {
                        isValid = false
                        break
                    }
                }
                if (!isValid) break
            }
        }

        // Set text color based on validity
        cell?.setTextColor(if (isValid) Color.parseColor("#504141") else Color.parseColor("#D91B24"))
    }

    private fun generateNewSudoku() {
        // Clear current state
        clearBoard()
        hintsUsed = 0
        updateHintButton()

        // Generate a complete valid Sudoku solution
        generateCompleteSudoku()

        // Create puzzle by removing numbers
        createPuzzleFromSolution()

        // Display puzzle
        displayPuzzle()

        textViewDifficulty.text = getString(R.string.difficulty_medium)
        Toast.makeText(this, getString(R.string.new_sudoku_generated), Toast.LENGTH_SHORT).show()
    }

    private fun clearBoard() {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                sudokuCells[row][col]?.apply {
                    setText("")
                    isEnabled = true
                    setTextColor(Color.parseColor("#504141"))
                    setBackgroundColor(
                        if ((row / 3 + col / 3) % 2 == 0) Color.parseColor("#FAFAFA")
                        else Color.parseColor("#F0F0F0")
                    )
                }
                sudokuPuzzle[row][col] = 0
                sudokuSolution[row][col] = 0
            }
        }
    }

    private fun generateCompleteSudoku() {
        // Simple Sudoku generation algorithm
        fillDiagonalBoxes()
        fillRemaining(0, 3)

        // Copy solution
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                sudokuSolution[row][col] = sudokuPuzzle[row][col]
            }
        }
    }

    private fun fillDiagonalBoxes() {
        for (box in 0 until 3) {
            fillBox(box * 3, box * 3)
        }
    }

    private fun fillBox(row: Int, col: Int) {
        val numbers = (1..9).shuffled()
        var index = 0

        for (i in 0 until 3) {
            for (j in 0 until 3) {
                sudokuPuzzle[row + i][col + j] = numbers[index++]
            }
        }
    }

    private fun fillRemaining(row: Int, col: Int): Boolean {
        var nextRow = row
        var nextCol = col + 1

        if (nextCol >= 9) {
            nextRow++
            nextCol = 0
        }

        if (nextRow >= 9) return true

        if (sudokuPuzzle[nextRow][nextCol] != 0) {
            return fillRemaining(nextRow, nextCol)
        }

        val numbers = (1..9).shuffled()
        for (num in numbers) {
            if (isSafe(nextRow, nextCol, num)) {
                sudokuPuzzle[nextRow][nextCol] = num
                if (fillRemaining(nextRow, nextCol)) {
                    return true
                }
                sudokuPuzzle[nextRow][nextCol] = 0
            }
        }

        return false
    }

    private fun isSafe(row: Int, col: Int, num: Int): Boolean {
        return !usedInRow(row, num) &&
               !usedInCol(col, num) &&
               !usedInBox(row - row % 3, col - col % 3, num)
    }

    private fun usedInRow(row: Int, num: Int): Boolean {
        for (col in 0 until 9) {
            if (sudokuPuzzle[row][col] == num) return true
        }
        return false
    }

    private fun usedInCol(col: Int, num: Int): Boolean {
        for (row in 0 until 9) {
            if (sudokuPuzzle[row][col] == num) return true
        }
        return false
    }

    private fun usedInBox(boxStartRow: Int, boxStartCol: Int, num: Int): Boolean {
        for (row in 0 until 3) {
            for (col in 0 until 3) {
                if (sudokuPuzzle[boxStartRow + row][boxStartCol + col] == num) return true
            }
        }
        return false
    }

    private fun createPuzzleFromSolution() {
        val cellsToRemove = 45 // Medium difficulty
        var removed = 0

        while (removed < cellsToRemove) {
            val row = Random.nextInt(9)
            val col = Random.nextInt(9)

            if (sudokuPuzzle[row][col] != 0) {
                sudokuPuzzle[row][col] = 0
                removed++
            }
        }
    }

    private fun displayPuzzle() {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cell = sudokuCells[row][col]
                if (sudokuPuzzle[row][col] != 0) {
                    cell?.setText(sudokuPuzzle[row][col].toString())
                    cell?.isEnabled = false
                    cell?.setBackgroundColor(Color.parseColor("#E0E0E0"))
                    cell?.setTextColor(Color.parseColor("#000000"))
                } else {
                    cell?.setText("")
                    cell?.isEnabled = true
                }
            }
        }
    }

    private fun checkSudokuSolution() {
        var isComplete = true
        var isValid = true

        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cellText = sudokuCells[row][col]?.text.toString()

                if (cellText.isEmpty()) {
                    isComplete = false
                } else {
                    val value = cellText.toInt()

                    // Temporarily clear the cell to check validity
                    sudokuCells[row][col]?.setText("")

                    if (!isCellValid(row, col, value)) {
                        isValid = false
                    }

                    // Restore the value
                    sudokuCells[row][col]?.setText(value.toString())
                }
            }
        }

        when {
            !isComplete -> Toast.makeText(this, getString(R.string.puzzle_incomplete), Toast.LENGTH_SHORT).show()
            !isValid -> Toast.makeText(this, getString(R.string.puzzle_invalid), Toast.LENGTH_LONG).show()
            else -> showWinDialog()
        }
    }

    private fun isCellValid(row: Int, col: Int, value: Int): Boolean {
        // Check row
        for (c in 0 until 9) {
            if (c != col && sudokuCells[row][c]?.text.toString() == value.toString()) {
                return false
            }
        }

        // Check column
        for (r in 0 until 9) {
            if (r != row && sudokuCells[r][col]?.text.toString() == value.toString()) {
                return false
            }
        }

        // Check 3x3 box
        val boxStartRow = (row / 3) * 3
        val boxStartCol = (col / 3) * 3

        for (r in boxStartRow until boxStartRow + 3) {
            for (c in boxStartCol until boxStartCol + 3) {
                if ((r != row || c != col) &&
                    sudokuCells[r][c]?.text.toString() == value.toString()) {
                    return false
                }
            }
        }

        return true
    }

    private fun provideHint() {
        if (hintsUsed >= maxHints) {
            Toast.makeText(this, getString(R.string.no_hints_left), Toast.LENGTH_SHORT).show()
            return
        }

        val emptyCells = mutableListOf<Pair<Int, Int>>()

        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (sudokuCells[row][col]?.text.toString().isEmpty()) {
                    emptyCells.add(Pair(row, col))
                }
            }
        }

        if (emptyCells.isNotEmpty()) {
            val randomCell = emptyCells.random()
            val row = randomCell.first
            val col = randomCell.second

            sudokuCells[row][col]?.apply {
                setText(sudokuSolution[row][col].toString())
                isEnabled = false
                setBackgroundColor(Color.parseColor("#FFE0B2"))
                setTextColor(Color.parseColor("#E65100"))
            }

            hintsUsed++
            updateHintButton()
            Toast.makeText(this, getString(R.string.hint_provided, hintsUsed, maxHints), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateHintButton() {
        buttonHint.text = getString(R.string.hint_button_format, maxHints - hintsUsed)
        buttonHint.isEnabled = hintsUsed < maxHints
    }

    private fun showWinDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.sudoku_solved, hintsUsed, maxHints))
            .setPositiveButton(getString(R.string.new_game)) { _, _ ->
                generateNewSudoku()
            }
            .setNegativeButton(getString(R.string.back_to_menu)) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}