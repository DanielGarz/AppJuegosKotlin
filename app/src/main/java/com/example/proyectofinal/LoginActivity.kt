package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString()

            when {
                email.isEmpty() -> {
                    editTextEmail.error = getString(R.string.email_required)
                    editTextEmail.requestFocus()
                }
                !isValidEmail(email) -> {
                    editTextEmail.error = getString(R.string.email_invalid)
                    editTextEmail.requestFocus()
                }
                password.isEmpty() -> {
                    editTextPassword.error = getString(R.string.password_required)
                    editTextPassword.requestFocus()
                }
                else -> {
                    performLogin(email, password)
                }
            }
        }

        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun performLogin(email: String, password: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("Email", null)
        val savedPasswordHash = sharedPreferences.getString("PasswordHash", null)

        if (savedEmail == null || savedPasswordHash == null) {
            Toast.makeText(this, getString(R.string.no_account), Toast.LENGTH_LONG).show()
            return
        }

        val enteredPasswordHash = hashPassword(password)

        if (email == savedEmail && enteredPasswordHash == savedPasswordHash) {
            // Save login session
            val editor = sharedPreferences.edit()
            editor.putBoolean("IsLoggedIn", true)
            editor.putLong("LoginTime", System.currentTimeMillis())
            editor.apply()

            Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_LONG).show()
        }
    }
}
