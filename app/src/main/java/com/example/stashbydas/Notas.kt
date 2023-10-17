package com.example.stashbydas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stashbydas.Clases.Anotaciones
import com.example.stashbydas.Clases.AnotacionesAdapter

class Notas : AppCompatActivity() {

        private val noteList = mutableListOf<Anotaciones>()
        private val noteAdapter = AnotacionesAdapter(noteList)

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_notas)

            val noteRecyclerView = findViewById<RecyclerView>(R.id.noteRecyclerView)
            noteRecyclerView.layoutManager = LinearLayoutManager(this)
            noteRecyclerView.adapter = noteAdapter

            val noteEditText = findViewById<EditText>(R.id.textoNotas)
            val saveButton = findViewById<Button>(R.id.saveButton)
            val buttonVolver= findViewById<Button>(R.id.buttonVolverNotas)
            saveButton.setOnClickListener {
                val noteText = noteEditText.text.toString()
                if (noteText.isNotEmpty()) {
                    val newNote = Anotaciones(noteList.size, noteText)
                    noteList.add(newNote)
                    noteAdapter.notifyItemInserted(noteList.size - 1)
                    noteEditText.text.clear()
                }
            }

            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    noteAdapter.removeNote(position)
                }
            })
            itemTouchHelper.attachToRecyclerView(noteRecyclerView)

            buttonVolver.setOnClickListener {
                val intent= Intent(this,MainActivity::class.java )
                startActivity(intent)
                finish()
            }
        }
    }

