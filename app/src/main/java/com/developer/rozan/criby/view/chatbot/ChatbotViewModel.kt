package com.developer.rozan.criby.view.chatbot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.rozan.criby.data.local.entity.ChatMessageEntity
import com.developer.rozan.criby.utils.Participant
import com.developer.rozan.criby.utils.formatText
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import kotlinx.coroutines.launch

class ChatbotViewModel(generativeModel: GenerativeModel) : ViewModel() {

    private val chat = generativeModel.startChat()

    private val _uiState: MutableLiveData<ChatUiState> =
        MutableLiveData(ChatUiState(chat.history.map { content ->
            ChatMessageEntity(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        }))

    val uiState: LiveData<ChatUiState> = _uiState

    fun sendMessage(userMessage: String) {
        // Add a pending message
        val currentState = _uiState.value ?: return
        currentState.addMessage(
            ChatMessageEntity(
                text = userMessage,
                participant = Participant.USER,
                isPending = true
            )
        )
        _uiState.postValue(currentState)

        // Add typing indicator
        currentState.addMessage(
            ChatMessageEntity(
                text = "Sedang mengetik...",
                participant = Participant.MODEL,
                isPending = true
            )
        )
        _uiState.postValue(currentState)

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(
                    "Kamu adalah AI yang membantu user untuk mengetahui tangisan bayi dari sumber-sumber yang ada di dalam internet dan juga detail mengenai tangisan bayi. Jika pertanyaan diluar dari topik tangisan bayi atau seputar bayi, tolak dengan halus. Saya ingin memahami lebih dalam tentang tangisan bayi dan bayi. Bisakah Anda memberikan penjelasan terkait dengan pertanyaan ini: $userMessage"
                )

                // Remove the typing indicator and add the actual response
                val formattedResponse = formatText(response.text ?: "Error getting response")
                currentState.replaceLastPendingMessage(
                    Participant.MODEL,
                    formattedResponse.toString()
                )
                _uiState.postValue(currentState)
            } catch (e: Exception) {
                currentState.replaceLastPendingMessage(
                    Participant.ERROR,
                    e.localizedMessage ?: "An error occurred"
                )
                _uiState.postValue(currentState)
            }
        }
    }
}