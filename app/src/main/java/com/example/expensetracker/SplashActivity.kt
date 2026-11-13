package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val layoutSplashContent = findViewById<LinearLayout>(R.id.layoutSplashContent)
        val viewGlow = findViewById<View>(R.id.viewGlow)

        // 1. Initial State: Hidden and slightly scaled down
        layoutSplashContent.alpha = 0f
        layoutSplashContent.scaleX = 0.92f
        layoutSplashContent.scaleY = 0.92f

        // 2. Gentle Fade-in & Scale Animation for the Logo Content
        layoutSplashContent.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(800)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // 3. Subtle Pulsing Glow Animation behind the Logo
        viewGlow.alpha = 0.4f
        viewGlow.animate()
            .alpha(0.85f)
            .scaleX(1.15f)
            .scaleY(1.15f)
            .setDuration(1000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        // 4. Smooth Transition after Splash Display (1.4 seconds)
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if this is the user's first time or returning user
            val targetIntent = if (UserManager.isFirstTime(this)) {
                Intent(this, WelcomeActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }

            startActivity(targetIntent)
            // Elegant fade cross-transition
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 1400)
    }
}
