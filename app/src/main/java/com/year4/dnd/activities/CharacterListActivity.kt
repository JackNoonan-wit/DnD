package com.year4.dnd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.year4.dnd.R
import com.year4.dnd.main.MainApp

class CharacterListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)
        app = application as MainApp
    }
}
