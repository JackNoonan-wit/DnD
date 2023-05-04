package com.year4.dnd.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.year4.dnd.R
import com.year4.dnd.adapters.CharacterAdapter
import com.year4.dnd.adapters.CharacterListener
import com.year4.dnd.databinding.ActivityCharacterListBinding
import com.year4.dnd.databinding.CardCharacterBinding
import com.year4.dnd.main.MainApp
import com.year4.dnd.models.DndModel



class CharacterListActivity : AppCompatActivity(), CharacterListener  {

    lateinit var app: MainApp
    private lateinit var binding: ActivityCharacterListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = CharacterAdapter(app.characters)
        binding.recyclerView.adapter = CharacterAdapter(app.characters.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, DndActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
               // notifyItemRangeChanged(0,app.characters.size)
                notifyItemRangeChanged(0,app.characters.findAll().size)
            }
        }

    override fun onCharacterClick(character: DndModel) {
        val launcherIntent = Intent(this, DndActivity::class.java)
        launcherIntent.putExtra("character_edit", character)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.characters.findAll().size)
            }
        }

}

/*class CharacterAdapter constructor(private var characters: List<DndModel>) :
    RecyclerView.Adapter<CharacterAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val character = characters[holder.adapterPosition]
        holder.bind(character)
    }

    override fun getItemCount(): Int = characters.size

    class MainHolder(private val binding : CardCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: DndModel) {
            binding.dndTitle.text = character.title
            binding.description.text = character.description
        }
    }



}*/


