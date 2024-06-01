package com.developer.rozan.criby.view.recordaudio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivityRecordAudioBinding
import com.developer.rozan.criby.view.chatbot.ChatbotActivity

class RecordAudioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordAudioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnChatbot.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }
    }
}