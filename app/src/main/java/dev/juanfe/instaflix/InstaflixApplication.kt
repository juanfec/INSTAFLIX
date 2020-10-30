package dev.juanfe.instaflix

import android.app.Application
import dev.juanfe.instaflix.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InstaflixApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@InstaflixApplication)
            modules(appModule)
        }
    }
}