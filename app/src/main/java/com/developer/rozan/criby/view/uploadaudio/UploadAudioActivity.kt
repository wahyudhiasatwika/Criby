package com.developer.rozan.criby.view.uploadaudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivityUploadAudioBinding
import com.developer.rozan.criby.view.chatbot.ChatbotActivity

class UploadAudioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadAudioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnChatbot.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }
    }
}