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
import com.developer.rozan.criby.view.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        startActivity(Intent(this, WelcomeActivity::class.java))
    }
}