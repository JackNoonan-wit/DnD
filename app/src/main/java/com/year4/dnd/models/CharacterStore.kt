package com.year4.dnd.models


interface CharacterStore {
    fun findAll(): List<DndModel>
    fun create(character: DndModel)
    fun update(character: DndModel)
    fun delete(character: DndModel)

}