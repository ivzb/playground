package com.example.contentprovider

import android.content.ContentResolver
import android.content.ContentValues
import android.database.ContentObserver
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import com.example.contentprovider.ui.theme.ContentProviderTheme
import android.os.Handler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ContentProviderTheme {
                Scaffold { innerPadding ->
                    NotesScreen(
                        contentResolver = contentResolver,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NotesScreen(contentResolver: ContentResolver, modifier: Modifier = Modifier) {
    var notes by remember { mutableStateOf(emptyList<NoteUi>()) }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    fun reload() {
        val cursor = contentResolver.query(NotesContract.CONTENT_URI, null, null, null, null)

        if (cursor != null) {
            val idCol = cursor.getColumnIndexOrThrow(NotesContract.Columns.ID)
            val titleCol = cursor.getColumnIndexOrThrow(NotesContract.Columns.TITLE)
            val bodyCol = cursor.getColumnIndexOrThrow(NotesContract.Columns.BODY)

            notes = buildList {
                while (cursor.moveToNext()) {
                    add(NoteUi(cursor.getLong(idCol), cursor.getString(titleCol), cursor.getString(bodyCol)))
                }
            }
        }
    }

    DisposableEffect(Unit) {
        reload()

        val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) = reload()
        }

        contentResolver.registerContentObserver(NotesContract.CONTENT_URI, true, observer)
        onDispose { contentResolver.unregisterContentObserver(observer) }
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            if (title.isBlank()) {
                return@Button
            }

            val values = ContentValues().apply {
                put(NotesContract.Columns.TITLE, title)
                put(NotesContract.Columns.BODY, body)
            }

            contentResolver.insert(NotesContract.CONTENT_URI, values)
            title = ""
            body = ""
        }) {
            Text("Add note")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(notes, key = { it.id }) { note ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(note.title, style = MaterialTheme.typography.titleMedium)
                        Text(note.body, style = MaterialTheme.typography.bodyMedium)
                    }

                    IconButton(onClick = {
                        contentResolver.delete(NotesContract.itemUri(note.id), null, null)
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete note")
                    }
                }
            }
        }
    }
}

private data class NoteUi(val id: Long, val title: String, val body: String)
