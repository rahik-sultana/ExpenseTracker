package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if user has already completed onboarding.
        // If yes, bypass this screen immediately and open MainActivity (Dashboard).
        if (!UserManager.isFirstTime(this)) {
            navigateToDashboard()
            return
        }

        setContentView(R.layout.activity_welcome)

        val etWelcomeName = findViewById<EditText>(R.id.etWelcomeName)
        val btnContinue = findViewById<MaterialButton>(R.id.btnWelcomeContinue)

        btnContinue.setOnClickListener {
            val name = etWelcomeName.text.toString().trim()

            if (name.isEmpty()) {
                etWelcomeName.error = "Please enter your name"
                etWelcomeName.requestFocus()
                return@setOnClickListener
            }

            // Save user name and mark onboarding as complete
            UserManager.saveUserName(this, name)
            UserManager.setFirstTimeCompleted(this)

            Toast.makeText(this, "Welcome, $name!", Toast.LENGTH_SHORT).show()
            navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, MainActivity::class.java)
        // Clear backstack so the user cannot press Back to return to the Welcome screen
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
