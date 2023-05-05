package com.year4.dnd.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.year4.dnd.R
import com.year4.dnd.adapters.CharacterAdapter
import com.year4.dnd.adapters.CharacterListener
import com.year4.dnd.databinding.ActivityCharacterListBinding
import com.year4.dnd.main.MainApp
import com.year4.dnd.models.DndModel


class CharacterListView : AppCompatActivity(), CharacterListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityCharacterListBinding
    lateinit var presenter: CharacterListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = CharacterListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadPlacemarks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddCharacter() }
           // R.id.item_map -> { presenter.doShowCharactersMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCharacterClick(character: DndModel, position: Int) {
        this.position = position
        presenter.doEditCharacter(character, this.position)
    }

    private fun loadPlacemarks() {
        binding.recyclerView.adapter = CharacterAdapter(presenter.getCharacters(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getCharacters().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
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


