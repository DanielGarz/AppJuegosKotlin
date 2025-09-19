package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.card.MaterialCardView

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val cardGame1: MaterialCardView = findViewById(R.id.cardGame1)
        val cardGame2: MaterialCardView = findViewById(R.id.cardGame2)
        val cardGame3: MaterialCardView = findViewById(R.id.cardGame3)
        val cardGame4: MaterialCardView = findViewById(R.id.cardGame4)

        // Sudoku
        cardGame1.setOnClickListener {
            val intent = Intent(this, SudokuActivity::class.java)
            startActivity(intent)
        }

        // Tic Tac Toe
        cardGame2.setOnClickListener {
            val intent = Intent(this, TicTacToeActivity::class.java)
            startActivity(intent)
        }

        // Placeholder for Game 3
        cardGame3.setOnClickListener {
            // Intent to Game3Activity - to be created
        }

        // Placeholder for Game 4
        cardGame4.setOnClickListener {
            // Intent to Game4Activity - to be created
        }
    }
}
