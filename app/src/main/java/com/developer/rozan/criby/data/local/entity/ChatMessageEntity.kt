package com.developer.rozan.criby.data.local.entity

import com.developer.rozan.criby.utils.Participant
import java.util.UUID

data class ChatMessageEntity(
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    val participant: Participant = Participant.USER,
    var isPending: Boolean = false
)
