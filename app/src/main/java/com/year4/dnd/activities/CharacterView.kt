package com.year4.dnd.activities

/*import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.year4.dnd.R
import com.year4.dnd.databinding.ActivityDndBinding
import com.year4.dnd.helpers.showImagePicker
import com.year4.dnd.main.MainApp
import com.year4.dnd.models.DndModel

import timber.log.Timber.i


/*
initial solution to adding characters to an array

class DndActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDndBinding
    var character = DndModel()
    val characters = ArrayList<DndModel>()

    fun add(character: DndModel): Boolean {
        return characters.add(character)
    }
    fun addCharacter(){
         add(DndModel(character.title))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDndBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Dnd Activity started")

        binding.btnAdd.setOnClickListener() {
            character.title = binding.dndTitle.text.toString()
            if (character.title.isNotEmpty()) {
                i("add Button Pressed: $character.title")
                i("all characters: $characters")
                for (i in characters.indices)


                addCharacter()
              //  println(listAllCharacters())
            }
            else {
                Snackbar
                    .make(it,"Please Enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}*/



//  private val Api = DndActivity(JSONSerializer(File("characters.json")))

//  class DndActivity (serializerType: Serializer): AppCompatActivity() {

   class DndActivity : AppCompatActivity() {



       //    private var serializer: Serializer = serializerType

       private lateinit var binding: ActivityDndBinding
       var character = DndModel()
       lateinit var app: MainApp
       private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

       override fun onCreate(savedInstanceState: Bundle?) {


           super.onCreate(savedInstanceState)

           binding = ActivityDndBinding.inflate(layoutInflater)
           setContentView(binding.root)

           binding.toolbarAdd.title = title
           setSupportActionBar(binding.toolbarAdd)


           var edit = false
           if (intent.hasExtra("character_edit")) {
               edit = true
               character = intent.extras?.getParcelable("character_edit")!!
               binding.dndTitle.setText(character.title)
               binding.description.setText(character.description)
               binding.btnAdd.setText(R.string.save_character)
               Picasso.get()
                   .load(character.image)
                   .into(binding.characterImage)
           }


           app = application as MainApp
           i("Character Creator started")


           binding.btnAdd.setOnClickListener() {
               character.title = binding.dndTitle.text.toString()
               character.description = binding.description.text.toString()
               character.abilities = binding.abilities.text.toString()
               character.age = binding.age.text.toString()
               if (character.title.isEmpty()) {
                   Snackbar.make(it, R.string.enter_character_name, Snackbar.LENGTH_LONG).show()
               } else {
                   if (edit) {
                       app.characters.update(character.copy())
                   } else {
                       app.characters.create(character.copy())
                   }
               }
               i("add Button Pressed: $character")
               setResult(RESULT_OK)
               finish()
           }


           binding.chooseImage.setOnClickListener {
               showImagePicker(imageIntentLauncher)
           }

       }
       override fun onCreateOptionsMenu(menu: Menu): Boolean {
           menuInflater.inflate(R.menu.menu_character, menu)
           return super.onCreateOptionsMenu(menu)
       }

       override fun onOptionsItemSelected(item: MenuItem): Boolean {
           when (item.itemId) {
               R.id.item_cancel -> {
                   finish()
               }
           }
           return super.onOptionsItemSelected(item)
       }

       private fun registerImagePickerCallback() {
           imageIntentLauncher =
               registerForActivityResult(ActivityResultContracts.StartActivityForResult())
               { result ->
                   when(result.resultCode){
                       RESULT_OK -> {
                           if (result.data != null) {
                               i("Got Result ${result.data!!.data}")
                               character.image = result.data!!.data!!
                               Picasso.get()
                                   .load(character.image)
                                   .into(binding.characterImage)
                           } // end of if
                       }
                       RESULT_CANCELED -> { } else -> { }
                   }
               }
       }


   }
*/

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.year4.dnd.R
import com.year4.dnd.databinding.ActivityDndBinding
import com.year4.dnd.models.DndModel
import timber.log.Timber.i

class CharacterView : AppCompatActivity() {

    private lateinit var binding: ActivityDndBinding
    private lateinit var presenter: CharacterPresenter
    var character = DndModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityDndBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = CharacterPresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheCharacter(binding.dndTitle.text.toString(), binding.description.text.toString())
            presenter.doSelectImage()
        }

        binding.characterLocation.setOnClickListener {
            presenter.cacheCharacter(binding.dndTitle.text.toString(), binding.description.text.toString())
            presenter.doSetLocation()
        }

        binding.btnAdd.setOnClickListener {
            if (binding.dndTitle.text.toString().isEmpty()) {
                Snackbar.make(binding.root, R.string.enter_character_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                presenter.doAddOrSave(binding.dndTitle.text.toString(), binding.description.text.toString())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_character, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showCharacter(character: DndModel) {
        binding.dndTitle.setText(character.title)
        binding.description.setText(character.description)
        binding.btnAdd.setText(R.string.save_character)
        Picasso.get()
            .load(character.image)
            .into(binding.characterImage)
        if (character.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_character_image)
        }
    }

    fun updateImage(image: Uri) {
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.characterImage)
        binding.chooseImage.setText(R.string.change_character_image)
    }
}
