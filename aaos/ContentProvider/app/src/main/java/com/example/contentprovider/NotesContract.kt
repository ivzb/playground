package com.example.contentprovider

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

object NotesContract {

    const val AUTHORITY = "com.example.contentprovider.provider"
    const val TABLE_NAME = "notes"

    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

    object Columns {

        const val ID = BaseColumns._ID
        const val TITLE = "title"
        const val BODY = "body"
        const val CREATED_AT = "created_at"

    }

    fun itemUri(id: Long): Uri = ContentUris.withAppendedId(CONTENT_URI, id)

}