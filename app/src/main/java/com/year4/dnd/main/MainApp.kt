package com.year4.dnd.main
import android.app.Application
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Character creator started started")
    }
}
