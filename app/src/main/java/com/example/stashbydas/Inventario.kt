package com.example.stashbydas

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stashbydas.Clases.GlobalVariables
import com.example.stashbydas.Clases.Producto
import com.example.stashbydas.Clases.ProductoAdapter

class Inventario : AppCompatActivity() {
    private val inventoryList = mutableListOf<Producto>()
    private lateinit var adapter: ProductoAdapter



    object DefaultProductos {
        val productList = listOf(
            Producto("Huevo", 10, 500, listOf("Alimento"), listOf("Supermercado","Feria","Almacen")),
            Producto("Arroz", 5, 1500, listOf("Alimento"), listOf("Supermercado","Feria","Almacen")),
            Producto("Polera", 20, 5000, listOf("Ropa"), listOf("Supermercado","Feria")),

            )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)
        inventoryList.addAll(DefaultProductos.productList)
        inventoryList.addAll(GlobalVariables.productList)
        GlobalVariables.productList.clear()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = ProductoAdapter(inventoryList, this::mostrarDetalleProducto, this::eliminarProducto)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun mostrarDetalleProducto(producto: Producto) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_detalle_producto, null)
        val dialogNameTextView = dialogView.findViewById<TextView>(R.id.dialogNameTextView)
        val dialogCantidadTextView = dialogView.findViewById<TextView>(R.id.dialogCantidadTextView)
        val dialogPrecioTextView = dialogView.findViewById<TextView>(R.id.dialogPrecioTextView)
        val dialogCategoriaTextView = dialogView.findViewById<TextView>(R.id.dialogCategoriaTextView)
        val dialogTiendaTextView = dialogView.findViewById<TextView>(R.id.dialogTiendaTextView)

        dialogNameTextView.text = "Nombre: ${producto.nombre}"
        dialogCantidadTextView.text = "Cantidad: ${producto.cantidad}"
        dialogPrecioTextView.text = "Precio: ${producto.precio}"
        dialogCategoriaTextView.text = "Categoría: ${producto.categoria.joinToString(", ")}"
        dialogTiendaTextView.text = "Tiendas: ${producto.tiendas.joinToString(", ")}"

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun eliminarProducto(producto: Producto) {
        if (producto.cantidad > 1) {
            producto.cantidad--
            adapter.notifyDataSetChanged()
        } else {
            inventoryList.remove(producto)
            adapter.notifyDataSetChanged()
        }
    }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()

    }
}