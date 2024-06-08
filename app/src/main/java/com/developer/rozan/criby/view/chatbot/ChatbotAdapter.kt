package com.developer.rozan.criby.view.chatbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.rozan.criby.databinding.ItemChatBinding
import com.developer.rozan.criby.utils.gone
import com.developer.rozan.criby.utils.visible

class ChatbotAdapter(private val chatMessages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatbotAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatMessages[position]
        holder.bind(chat)
    }

    class ViewHolder(private val view: ItemChatBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(chatMessage: ChatMessage) {
            when (chatMessage.participant) {
                Participant.MODEL -> {
                    view.isiChatLeft.visible()
                    view.isiChatLeft.text = chatMessage.text
                    view.isiChatError.gone()
                    view.isiChatRight.gone()
                }

                Participant.ERROR -> {
                    view.isiChatLeft.gone()
                    view.isiChatError.visible()
                    view.isiChatError.text = chatMessage.text
                    view.isiChatRight.gone()
                }

                Participant.USER -> {
                    view.isiChatLeft.gone()
                    view.isiChatError.gone()
                    view.isiChatRight.visible()
                    view.isiChatRight.text = chatMessage.text
                }
            }
        }
    }
}