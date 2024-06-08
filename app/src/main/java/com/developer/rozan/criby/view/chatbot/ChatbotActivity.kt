package com.developer.rozan.criby.view.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.criby.data.factory.ViewModelFactory
import com.developer.rozan.criby.databinding.ActivityChatbotBinding
import com.developer.rozan.criby.utils.closeKeyboard
import com.developer.rozan.criby.utils.showKeyboard
import com.developer.rozan.criby.utils.showToast
import kotlinx.coroutines.launch

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var chatbotViewModel: ChatbotViewModel
    private lateinit var chatbotAdapter: ChatbotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar("Kembali")

        chatbotViewModel = obtainViewModel(this)

        binding.btnSend.setOnClickListener {
            val text = binding.tfTextHere.editText?.text.toString()

            if (validateTextChat(text)) {
                chatbotViewModel.sendMessage(text)
                binding.tfTextHere.editText?.setText("")
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                setUpChat()
            }
        }
    }

    private fun setUpChat() {
        binding.rvListChat.layoutManager = LinearLayoutManager(this)
        binding.rvListChat.setHasFixedSize(true)

        chatbotViewModel.uiState.observe(this) {
            chatbotAdapter = ChatbotAdapter(it.messages)
            binding.rvListChat.adapter = chatbotAdapter
            binding.rvListChat.scrollToPosition(0)
        }
    }

    private fun validateTextChat(text: String): Boolean {
        closeKeyboard(this, binding.btnSend)

        return when {
            text.isEmpty() -> {
                binding.tfTextHere.requestFocus()
                showKeyboard(this, binding.tfTextHere.editText!!)
                showToast(this, "Silahkan masukkan pesan.")
                false
            }

            else -> {
                true
            }
        }
    }

    private fun setToolbar(title: String) {
        setSupportActionBar(binding.toolbarChatbot)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            this.title = title
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun obtainViewModel(activity: AppCompatActivity): ChatbotViewModel {
        val factory = ViewModelFactory.getInstance()
        return ViewModelProvider(activity, factory)[ChatbotViewModel::class.java]
    }
}