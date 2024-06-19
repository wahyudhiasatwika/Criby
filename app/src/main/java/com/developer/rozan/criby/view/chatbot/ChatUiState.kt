package com.developer.rozan.criby.view.chatbot

import androidx.compose.runtime.toMutableStateList
import com.developer.rozan.criby.data.local.entity.ChatMessageEntity
import com.developer.rozan.criby.utils.Participant

class ChatUiState(
    messages: List<ChatMessageEntity> = emptyList()
) {
    private val _messages: MutableList<ChatMessageEntity> = messages.toMutableStateList()
    val messages: List<ChatMessageEntity> = _messages

    fun addMessage(msg: ChatMessageEntity) {
        _messages.add(msg)
    }

    fun replaceLastPendingMessage(role: Participant, newText: String) {
        val lastMessage = _messages.lastOrNull { it.isPending }
        lastMessage?.let {
            val newMessage = it.copy(participant = role, text = newText, isPending = false)
            val lastIndex = _messages.indexOf(it)
            _messages[lastIndex] = newMessage
        }
    }
}
