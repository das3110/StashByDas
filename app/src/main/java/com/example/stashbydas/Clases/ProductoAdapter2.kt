package com.example.stashbydas.Clases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stashbydas.R

class ProductoAdapter2(
    private val productos: MutableList<Producto>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ProductoAdapter2.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_compra, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)

        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)

        }
    }

    override fun getItemCount() = productos.size

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre = itemView.findViewById<TextView>(R.id.nombretxt)
        private val tvCantidad = itemView.findViewById<TextView>(R.id.cantidadtxt)
        private val tvPrecio = itemView.findViewById<TextView>(R.id.preciotxt)
        val btnDelete = itemView.findViewById<Button>(R.id.deleteButton)

        fun bind(producto: Producto) {
            tvNombre.text = producto.nombre
            tvCantidad.text = producto.cantidad.toString()
            tvPrecio.text = "$" + producto.precio.toString()
        }
    }
}