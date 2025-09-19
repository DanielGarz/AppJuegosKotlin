package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast

class SudokuActivity : AppCompatActivity() {

    // TODO: Define your Sudoku board representation (e.g., a 2D array)
    // TODO: Implement Sudoku generation logic
    // TODO: Implement Sudoku validation logic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)

        val gridLayoutSudoku: GridLayout = findViewById(R.id.gridLayoutSudoku)
        val buttonNewGame: Button = findViewById(R.id.buttonNewGameSudoku)
        val buttonCheck: Button = findViewById(R.id.buttonCheckSudoku)

        // TODO: Populate the GridLayout with EditTexts or custom views for each cell
        // You'''ll need to dynamically create and add 81 cells to gridLayoutSudoku
        // or ensure they are all defined in the XML and then get references to them.

        buttonNewGame.setOnClickListener {
            // TODO: Implement new game logic (generate and display a new puzzle)
            Toast.makeText(this, "New Sudoku Game (Not Implemented)", Toast.LENGTH_SHORT).show()
        }

        buttonCheck.setOnClickListener {
            // TODO: Implement Sudoku check logic (validate the current board)
            Toast.makeText(this, "Check Sudoku (Not Implemented)", Toast.LENGTH_SHORT).show()
        }
    }
}