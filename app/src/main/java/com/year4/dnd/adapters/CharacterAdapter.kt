package com.year4.dnd.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.year4.dnd.databinding.CardCharacterBinding
import com.year4.dnd.models.DndModel

interface CharacterListener {
    fun onCharacterClick(character: DndModel)
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
class CharacterAdapter constructor(private var characters: List<DndModel>,
                                   private val listener: CharacterListener) :
    RecyclerView.Adapter<CharacterAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCharacterBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val character = characters[holder.adapterPosition]
        holder.bind(character, listener)
    }

    override fun getItemCount(): Int = characters.size

    class MainHolder(private val binding : CardCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: DndModel, listener: CharacterListener) {
            binding.dndTitle.text = character.title
            binding.description.text = character.description
            binding.root.setOnClickListener { listener.onCharacterClick(character) }
        }
    }
}
