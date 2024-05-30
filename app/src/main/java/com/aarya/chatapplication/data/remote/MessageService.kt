package com.aarya.chatapplication.data.remote

import com.aarya.chatapplication.domain.model.Message

interface MessageService {
    suspend fun getAllMessages(): List<Message>

    companion object{
        const val BASE_URL = "http://aaryaworks.tech"
    }

    sealed class EndPoints(val url: String){
        object GetAllMessages: EndPoints("$BASE_URL/messages")
    }
}