package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.card.MaterialCardView

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Check if user is logged in
        checkLoginStatus()

        // Initialize views
        val cardGame1: MaterialCardView = findViewById(R.id.cardGame1)
        val cardGame2: MaterialCardView = findViewById(R.id.cardGame2)
        val cardGame3: MaterialCardView = findViewById(R.id.cardGame3)
        val cardGame4: MaterialCardView = findViewById(R.id.cardGame4)
        val logoutIcon: ImageView = findViewById(R.id.logoutIcon)

        // Display welcome message
        displayWelcomeMessage()

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

        // Memory Game
        cardGame3.setOnClickListener {
            val intent = Intent(this, MemoryGameActivity::class.java)
            startActivity(intent)
        }

        // Quiz Game
        cardGame4.setOnClickListener {
            val intent = Intent(this, QuizGameActivity::class.java)
            startActivity(intent)
        }

        // Logout functionality
        logoutIcon.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun checkLoginStatus() {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("IsLoggedIn", false)

        if (!isLoggedIn) {
            // User is not logged in, redirect to login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun displayWelcomeMessage() {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("Name", "User")
        val welcomeText = findViewById<TextView>(R.id.textViewWelcome)
        welcomeText.text = "¡Bienvenido de vuelta, $userName!"
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.logout_confirmation))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                performLogout()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun performLogout() {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("IsLoggedIn", false)
        editor.remove("LoginTime")
        editor.apply()

        Toast.makeText(this, getString(R.string.logout_successful), Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Prevent going back to login screen
        showLogoutDialog()
    }
}
