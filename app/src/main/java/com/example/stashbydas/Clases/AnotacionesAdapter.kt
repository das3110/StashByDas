package com.example.stashbydas.Clases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stashbydas.R

class AnotacionesAdapter(private val noteList: MutableList<Anotaciones>) : RecyclerView.Adapter<AnotacionesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteText: TextView = itemView.findViewById(R.id.noteTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_anotacion, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = noteList[position]
        holder.noteText.text = currentNote.text
    }
     fun removeNote(position: Int) {
        if (position in 0 until noteList.size) {
            noteList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    override fun getItemCount() = noteList.size

}