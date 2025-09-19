package com.example.proyectofinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Start LoginActivity
        startActivity(Intent(this, LoginActivity::class.java))
        // Finish MainActivity so the user cannot navigate back to it
        finish()
    }
}