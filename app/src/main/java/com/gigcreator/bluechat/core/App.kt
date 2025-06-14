package com.gigcreator.bluechat.core

import android.app.Application
import com.gigcreator.bluechat.core.di.appModule
import com.gigcreator.data.feature.permission.di.permissionModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(permissionModule, appModule)
        }
    }
}