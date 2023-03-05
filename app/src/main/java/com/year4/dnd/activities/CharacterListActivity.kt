package com.year4.dnd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.year4.dnd.databinding.ActivityCharacterListBinding
import com.year4.dnd.databinding.CardCharacterBinding
import com.year4.dnd.main.MainApp
import com.year4.dnd.models.DndModel

class CharacterListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityCharacterListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = CharacterAdapter(app.characters)
    }
}

class CharacterAdapter constructor(private var characters: List<DndModel>) :
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
}
