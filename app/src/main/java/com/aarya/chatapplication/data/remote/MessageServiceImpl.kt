package com.aarya.chatapplication.data.remote

import com.aarya.chatapplication.data.remote.dto.MessageDto
import com.aarya.chatapplication.domain.model.Message
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.*


class MessageServiceImpl(
    private val client: HttpClient
): MessageService {
    override suspend fun getAllMessages(): List<Message> {
        return try {
            val response = client.get(MessageService.EndPoints.GetAllMessages.url)
            if (response.status.isSuccess()){
                val messageDtos : List<MessageDto> = response.body()
                messageDtos.map { it.toMessage() }
            }else{
                return emptyList()
            }
        }catch (e: Exception){
            emptyList()
        }
    }
}