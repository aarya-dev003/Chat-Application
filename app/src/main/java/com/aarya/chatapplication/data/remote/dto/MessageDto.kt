package com.aarya.chatapplication.data.remote.dto

import android.icu.text.DateFormat
import com.aarya.chatapplication.domain.model.Message
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class MessageDto (
    val text : String,
    val timeStamp: Long,
    val userName : String,
    val id : String
){
    fun toMessage() : Message{
        val date = Date(timeStamp)
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.DEFAULT)
            .format(date)

        return Message(
            text = text,
            userName = userName,
            formattedTime = formattedDate
        )
    }
}