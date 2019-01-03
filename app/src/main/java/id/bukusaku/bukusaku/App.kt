package id.bukusaku.bukusaku

import android.app.Application
import id.bukusaku.bukusaku.di.*
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        startKoin(this, listOf(dataModule, networkModule, presenterModule, schedulersModule, dbModule))
    }
}