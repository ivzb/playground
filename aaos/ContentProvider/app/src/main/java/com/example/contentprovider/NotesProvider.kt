package com.example.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

class NotesProvider : ContentProvider() {

    private lateinit var appContext: Context

    override fun onCreate(): Boolean {
        appContext = checkNotNull(context) { "ContentProvider attached without a Context" }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor {
        val columns = projection ?: arrayOf(
            NotesContract.Columns.ID, NotesContract.Columns.TITLE, NotesContract.Columns.BODY
        )
        val cursor = MatrixCursor(columns)

        val rows = when (matcher.match(uri)) {
            NOTES -> NotesStore.notes
            NOTE_ID -> {
                val id = ContentUris.parseId(uri)
                NotesStore.notes.filter { it.id == id }
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        for (note in rows) {
            cursor.addRow(columns.map { column ->
                when (column) {
                    NotesContract.Columns.ID -> note.id
                    NotesContract.Columns.TITLE -> note.title
                    NotesContract.Columns.BODY -> note.body
                    else -> null
                }
            })
        }

        cursor.setNotificationUri(appContext.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        require(matcher.match(uri) == NOTES) { "Insert not supported for URI: $uri" }
        requireNotNull(values)

        val note = NotesStore.insert(
            title = values.getAsString(NotesContract.Columns.TITLE) ?: "",
            body = values.getAsString(NotesContract.Columns.BODY) ?: ""
        )
        val resultUri = NotesContract.itemUri(note.id)
        appContext.contentResolver.notifyChange(resultUri, null)

        return resultUri
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        require(matcher.match(uri) == NOTE_ID) { "Update requires an item URI: $uri" }
        val id = ContentUris.parseId(uri)
        val count = NotesStore.update(
            id,
            values?.getAsString(NotesContract.Columns.TITLE),
            values?.getAsString(NotesContract.Columns.BODY)
        )

        if (count > 0) {
            appContext.contentResolver.notifyChange(uri, null)
        }

        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        require(matcher.match(uri) == NOTE_ID) { "Delete requires an item URI: $uri" }
        val count = NotesStore.delete(ContentUris.parseId(uri))

        if (count > 0) {
            appContext.contentResolver.notifyChange(uri, null)
        }

        return count
    }

    override fun getType(uri: Uri): String = when (matcher.match(uri)) {
        NOTES -> "com.example.notes"
        NOTE_ID -> "com.example.notes"
        else -> throw IllegalArgumentException("Unknown URI: $uri")
    }

    companion object {

        private const val NOTES = 0
        private const val NOTE_ID = 1

        private val matcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(NotesContract.AUTHORITY, NotesContract.TABLE_NAME, NOTES)
            addURI(NotesContract.AUTHORITY, "${NotesContract.TABLE_NAME}/#", NOTE_ID)
        }

    }

}