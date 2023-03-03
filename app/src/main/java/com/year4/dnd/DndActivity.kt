package com.year4.dnd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.year4.dnd.databinding.ActivityDndBinding
import timber.log.Timber
import timber.log.Timber.i

class DndActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDndBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dnd)

        binding = ActivityDndBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Dnd Activity started")

        binding.btnAdd.setOnClickListener() {
            val dndTitle = binding.dndTitle.text.toString()
            if (dndTitle.isNotEmpty()) {
                i("add Button Pressed: $dndTitle")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
}