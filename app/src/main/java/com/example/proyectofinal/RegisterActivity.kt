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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString()

            when {
                name.isEmpty() -> {
                    editTextName.error = getString(R.string.name_required)
                    editTextName.requestFocus()
                }
                name.length < 2 -> {
                    editTextName.error = getString(R.string.name_too_short)
                    editTextName.requestFocus()
                }
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
                !isValidPassword(password) -> {
                    editTextPassword.error = getString(R.string.password_weak)
                    editTextPassword.requestFocus()
                }
                else -> {
                    performRegistration(name, email, password)
                }
            }
        }

        textViewLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        if (password.length < 6) return false

        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }

        return hasLetter && hasDigit
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun performRegistration(name: String, email: String, password: String) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Check if user already exists
        val existingEmail = sharedPreferences.getString("Email", null)
        if (existingEmail == email) {
            Toast.makeText(this, getString(R.string.account_exists), Toast.LENGTH_LONG).show()
            return
        }

        // Hash password before storing
        val passwordHash = hashPassword(password)

        // Save credentials to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("Name", name)
        editor.putString("Email", email)
        editor.putString("PasswordHash", passwordHash)
        editor.putLong("RegistrationTime", System.currentTimeMillis())
        editor.apply()

        Toast.makeText(this, getString(R.string.registration_successful) + " ¡Bienvenido, $name!", Toast.LENGTH_SHORT).show()

        // Navigate to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
