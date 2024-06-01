package com.developer.rozan.criby.view.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developer.rozan.criby.R
import com.developer.rozan.criby.databinding.ActivityChatbotBinding

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}