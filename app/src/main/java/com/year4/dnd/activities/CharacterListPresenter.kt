package com.year4.dnd.activities

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.year4.dnd.main.MainApp
import com.year4.dnd.models.DndModel

class CharacterListPresenter(val view: CharacterListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getCharacters() = app.characters.findAll()

    fun doAddCharacter() {
        val launcherIntent = Intent(view, CharacterView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditCharacter(character: DndModel, pos: Int) {
        val launcherIntent = Intent(view, CharacterView::class.java)
        launcherIntent.putExtra("character_edit", character)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

   /* fun doShowCharactersMap() {
        val launcherIntent = Intent(view, CharacterMapsActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }*/

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {  }
    }
}
