package com.developer.rozan.criby.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivitySplashScreenBinding
import com.developer.rozan.criby.utils.DELAY_1500L
import com.developer.rozan.criby.view.home.HomeActivity
import com.developer.rozan.criby.view.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.image.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.fade_in
            )
        )
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(DELAY_1500L)
                toNextActivity()
                finish()
            }
        }
    }

    private fun toNextActivity() {
        if (auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}