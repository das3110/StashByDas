package com.example.stashbydas.Clases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stashbydas.R

class ProductoAdapter(private val items: List<Producto>,
                      private val onItemClick: (Producto) -> Unit,
                      private val onDeleteClick: (Producto) -> Unit) :
    RecyclerView.Adapter<ProductoAdapter.InventoryViewHolder>() {

    class InventoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return InventoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val currentItem = items[position]
        holder.nameTextView.text = currentItem.nombre
        holder.quantityTextView.text = "Cantidad: ${currentItem.cantidad}"
        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(currentItem)
        }
    }

    override fun getItemCount() = items.size
}