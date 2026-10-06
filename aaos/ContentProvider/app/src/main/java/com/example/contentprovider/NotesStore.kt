package com.example.contentprovider

data class Note(val id: Long, var title: String, var body: String)

object NotesStore {
    private var nextId = 3L

    val notes = mutableListOf(
        Note(1L, "Pick up groceries", "Milk, eggs, bread"),
        Note(2L, "Call Alice", "Discuss the AAOS demo")
    )

    fun insert(title: String, body: String): Note {
        val note = Note(nextId++, title, body)
        notes.add(note)
        return note
    }

    fun update(id: Long, title: String?, body: String?): Int {
        val note = notes.find { it.id == id } ?: return 0
        title?.let { note.title = it }
        body?.let { note.body = it }
        return 1
    }

    fun delete(id: Long): Int = if (notes.removeIf { it.id == id }) 1 else 0

}