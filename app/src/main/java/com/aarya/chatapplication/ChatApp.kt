package com.aarya.chatapplication

import android.app.Application
import com.aarya.chatapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ChatApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@ChatApp)
            modules(appModule)
        }
    }
}