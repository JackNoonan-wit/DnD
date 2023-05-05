package com.year4.dnd.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.year4.dnd.helpers.*
//import com.year4.dnd.helpers.exists
//import com.year4.dnd.helpers.read
//import com.year4.dnd.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "characters.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<DndModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class CharacterJSONStore(private val context: Context) : CharacterStore {

    var characters = mutableListOf<DndModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<DndModel> {
        logAll()
        return characters
    }

    override fun create(character: DndModel) {
        character.id = generateRandomId()
        characters.add(character)
        serialize()
    }


    override fun update(character: DndModel) {
        val charactersList = findAll() as ArrayList<DndModel>
        var foundCharacter: DndModel? = charactersList.find { p -> p.id == character.id }
        if (foundCharacter != null) {
            foundCharacter.title = character.title
            foundCharacter.description = character.description
            foundCharacter.image = character.image
            foundCharacter.lat = character.lat
            foundCharacter.lng = character.lng
            foundCharacter.zoom = character.zoom
        }
        serialize()
    }
    override fun delete(character: DndModel) {
        characters.remove(character)
        serialize()
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(characters, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        characters = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        characters.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
