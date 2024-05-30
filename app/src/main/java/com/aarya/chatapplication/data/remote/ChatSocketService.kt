package com.aarya.chatapplication.data.remote

import com.aarya.chatapplication.domain.model.Message
import com.aarya.chatapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {
    suspend fun initSession(
        username : String
    ): Resource<Unit>

    suspend fun sendMessages(
        message: String
    )

    fun observeMessages() : Flow<Message>

    suspend fun closeSession()

    companion object{
        const val BASE_URL = "ws://192.168.1.8:8080"
    }

    sealed class EndPoints(val url: String){
        object ChatSocket: EndPoints("$BASE_URL/chat-socket")
    }
}