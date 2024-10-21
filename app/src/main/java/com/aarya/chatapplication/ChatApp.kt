package com.aarya.chatapplication

import android.app.Application
import com.aarya.chatapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class ChatApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{

            androidLogger()

            printLogger(Level.DEBUG)
            androidContext(this@ChatApp)
            modules(appModule)
        }
    }
}