package com.year4.dnd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.year4.dnd.R
import com.year4.dnd.databinding.ActivityDndBinding
import com.year4.dnd.models.DndModel
import timber.log.Timber
import timber.log.Timber.i

class DndActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDndBinding
    var dnd = DndModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Dnd Activity started")

        binding.btnAdd.setOnClickListener() {
            dnd.title = binding.dndTitle.text.toString()
            if (dnd.title.isNotEmpty()) {
                i("add Button Pressed: $dnd.title")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}