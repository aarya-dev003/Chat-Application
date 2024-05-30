package com.aarya.chatapplication.presentation.chat

import com.aarya.chatapplication.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
