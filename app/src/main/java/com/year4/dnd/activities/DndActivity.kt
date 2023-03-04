package com.year4.dnd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.year4.dnd.R
import com.year4.dnd.databinding.ActivityDndBinding
import com.year4.dnd.models.DndModel
import timber.log.Timber
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

class DndActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDndBinding
    var character = DndModel()
    val characters = ArrayList<DndModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDndBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())
        i("Character Activity started")

        binding.btnAdd.setOnClickListener() {
            character.title = binding.dndTitle.text.toString()
            if (character.title.isNotEmpty()) {
                characters.add(character.copy())
                i("add Button Pressed: ${character}")
                for (i in characters.indices)
                { i("Character[$i]:${this.characters[i]}") }
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}