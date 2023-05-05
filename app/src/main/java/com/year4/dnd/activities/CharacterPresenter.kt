package com.year4.dnd.activities

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.year4.dnd.databinding.ActivityDndBinding
import com.year4.dnd.main.MainApp
import com.year4.dnd.models.Location
import com.year4.dnd.models.DndModel
import com.year4.dnd.helpers.showImagePicker
import timber.log.Timber

class CharacterPresenter(private val view: CharacterView) {

    var character = DndModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityDndBinding = ActivityDndBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("character_edit")) {
            edit = true
            character = view.intent.extras?.getParcelable("character_edit")!!
            view.showCharacter(character)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }
    fun doAddOrSave(title: String, description: String) {
        character.title = title
        character.description = description
        if (edit) {
            app.characters.update(character)
        } else {
            app.characters.create(character)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }
    fun doCancel() {
        view.finish()
    }
    fun doDelete() {
        view.setResult(99)
        app.characters.delete(character)
        view.finish()
    }
    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }
    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (character.zoom != 0f) {
            location.lat =  character.lat
            location.lng = character.lng
            location.zoom = character.zoom
        }
        val launcherIntent = Intent(view,EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)

    }
    fun cacheCharacter (title: String, description: String) {
        character.title = title;
        character.description = description
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            character.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(character.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(character.image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }            }    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            character.lat = location.lat
                            character.lng = location.lng
                            character.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }            }    }}
