package id.bukusaku.bukusaku

import android.app.Application
import id.bukusaku.bukusaku.di.*
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            this, listOf(
                dataModule, networkModule, presenterModule,
                schedulersModule, dbModule, spModule
            )
        )
    }
}