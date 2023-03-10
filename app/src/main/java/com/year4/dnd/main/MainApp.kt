package com.year4.dnd.main
import android.app.Application
import com.year4.dnd.models.DndModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val characters = ArrayList<DndModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Character creator started started")
        // characters.add(DndModel("John", "irish","26", "fireball"))
        // characters.add(DndModel("Sarah", "british","28", "fireball"))
        // characters.add(DndModel("Jones", "german","24", "fireball"))
    }
}
