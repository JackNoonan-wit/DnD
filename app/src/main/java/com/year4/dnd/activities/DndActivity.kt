package com.year4.dnd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.year4.dnd.R
import com.year4.dnd.databinding.ActivityDndBinding
import com.year4.dnd.main.MainApp
import com.year4.dnd.models.DndModel
import com.year4.dnd.persistence.JSONSerializer
import com.year4.dnd.persistence.Serializer

import timber.log.Timber
import timber.log.Timber.i
import java.io.File


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
               setResult(RESULT_OK)
               finish()
           }
           binding.chooseImage.setOnClickListener {
               i("Select image")
           }

       }
   }





