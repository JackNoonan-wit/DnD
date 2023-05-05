package com.year4.dnd.models

import com.year4.dnd.models.CharacterStore
import com.year4.dnd.models.DndModel


import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class CharacterMemStore : CharacterStore {

    val characters = ArrayList<DndModel>()

    override fun findAll(): List<DndModel> {
        return characters
    }

    override fun create(character: DndModel) {
        character.id = getId()
        characters.add(character)
        logAll()
    }

    override fun update(character: DndModel) {
        var foundCharacter: DndModel? = characters.find { p -> p.id == character.id }
        if (foundCharacter != null) {
            foundCharacter.title = character.title
            foundCharacter.description = character.description
            foundCharacter.image = character.image
            foundCharacter.lat = character.lat
            foundCharacter.lng = character.lng
            foundCharacter.zoom = character.zoom
            logAll()
        }
    }

    private fun logAll() {
        characters.forEach { i("$it") }
    }
}
