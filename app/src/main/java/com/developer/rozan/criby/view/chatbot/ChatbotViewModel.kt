package com.developer.rozan.criby.view.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatbotViewModel(generativeModel: GenerativeModel) : ViewModel() {

    private val chat = generativeModel.startChat()

    private val _uiState: MutableLiveData<ChatUiState> =
        MutableLiveData(ChatUiState(chat.history.map { content ->
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        }))

    val uiState: LiveData<ChatUiState> = _uiState

    fun sendMessage(userMessage: String) {
        // Add a pending message
        _uiState.value?.addMessage(
            ChatMessage(
                text = userMessage,
                participant = Participant.USER,
                isPending = true
            )
        )

        viewModelScope.launch {
            try {
                val response =
                    chat.sendMessage("Kamu adalah AI yang membantu user untuk mengetahui tangisan bayi dari sumber-sumber yang ada di dalam internet dan juga detail mengenai tangisan bayi, jika pertanyaan diluar dari topik tangisan bayi atau seputar bayi tolak dengan halus. Saya ingin memahami lebih dalam tentang tangisan bayi dan bayi. Bisakah Anda memberikan penjelasan terkait dengan pertanyaan ini : $userMessage")

                _uiState.value?.replaceLastPendingMessage()

                response.text?.let { modelResponse ->
                    _uiState.value?.addMessage(
                        ChatMessage(
                            text = modelResponse,
                            participant = Participant.MODEL,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value?.replaceLastPendingMessage()
                _uiState.value?.addMessage(
                    ChatMessage(
                        text = e.localizedMessage,
                        participant = Participant.ERROR
                    )
                )
            }
        }
    }
}