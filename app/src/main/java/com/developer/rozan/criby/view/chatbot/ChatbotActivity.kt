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
    private val chatbotAdapter by lazy { ChatbotAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar("Kembali")
        setupViewModel()
        setupRecyclerView()
        setupChat()

        binding.btnSend.setOnClickListener {
            val text = binding.tfTextHere.text.toString()

            if (validateTextChat(text)) {
                chatbotViewModel.sendMessage(text)
                binding.tfTextHere.setText("")
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvListChat.apply {
            layoutManager = LinearLayoutManager(this@ChatbotActivity)
            setHasFixedSize(true)
            adapter = chatbotAdapter
        }
    }

    private fun setupViewModel() {
        chatbotViewModel = obtainViewModel(this)
    }

    private fun setupChat() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatbotViewModel.uiState.observe(this@ChatbotActivity) {
                    chatbotAdapter.updateMessages(it.messages)
                    binding.rvListChat.scrollToPosition(it.messages.size - 1)
                }
            }
        }
    }

    private fun validateTextChat(text: String): Boolean {
        closeKeyboard(this, binding.btnSend)

        return when {
            text.isEmpty() -> {
                binding.tfTextHere.requestFocus()
                showKeyboard(this, binding.tfTextHere)
                showToast(this, "Silahkan masukkan pesan.")
                false
            }

            else -> {
                true
            }
        }
    }

    private fun setupToolbar(title: String) {
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
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ChatbotViewModel::class.java]
    }
}