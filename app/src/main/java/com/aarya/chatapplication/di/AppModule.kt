package com.aarya.chatapplication.di

import com.aarya.chatapplication.data.remote.ChatSocketService
import com.aarya.chatapplication.data.remote.ChatSocketServiceImpl
import com.aarya.chatapplication.data.remote.MessageService
import com.aarya.chatapplication.data.remote.MessageServiceImpl
import com.aarya.chatapplication.presentation.chat.ChatViewModel
import com.aarya.chatapplication.presentation.username.UsernameViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single{
        HttpClient(CIO){
            install(Logging)
            install(WebSockets)
            install(ContentNegotiation){
                json()
            }

            install(Logging) {
                level = LogLevel.BODY
            }
            defaultRequest {
                header("Accept", "application/json")
            }
        }
    }


    single<MessageService>{MessageServiceImpl(get())}
    single<ChatSocketService>{ ChatSocketServiceImpl(get()) }
    viewModel{ UsernameViewModel()}
    viewModel {
//        (savedStateHandle: SavedStateHandle) ->
        ChatViewModel(
            get(),
            get(),
            get()
        )
    }
}