package com.year4.dnd.main
import android.app.Application
import com.year4.dnd.models.CharacterJSONStore
import timber.log.Timber
import timber.log.Timber.i
import com.year4.dnd.models.CharacterStore


class MainApp : Application() {

    lateinit var characters: CharacterStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        characters = CharacterJSONStore(applicationContext)
        //characters = CharacterMemStore
        i("Character creator started started")

    }
}
