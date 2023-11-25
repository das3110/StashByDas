package com.example.stashbydas

import StorageUtil
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        if (inventoryList.isEmpty()) {
            cargarProductos()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = ProductoAdapter(inventoryList, this::mostrarDetalleProducto, this::eliminarProducto)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val btnOrdenar: Button = findViewById(R.id.btnOrdenar)
        btnOrdenar.setOnClickListener { mostrarMenuOrdenamiento(it) }
        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtrarProductos(newText)
                return true
            }
        })
    }

    private fun mostrarDetalleProducto(producto: Producto) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_detalle_producto, null)
        val dialogNameTextView = dialogView.findViewById<TextView>(R.id.dialogNameTextView)
        val dialogCantidadTextView = dialogView.findViewById<TextView>(R.id.dialogCantidadTextView)
        val dialogPrecioTextView = dialogView.findViewById<TextView>(R.id.dialogPrecioTextView)
        val dialogCategoriaTextView = dialogView.findViewById<TextView>(R.id.dialogCategoriaTextView)
        val dialogTiendaTextView = dialogView.findViewById<TextView>(R.id.dialogTiendaTextView)

        dialogNameTextView.text = getString(R.string.Nombre) + producto.nombre
        dialogCantidadTextView.text = getString(R.string.Cantidad) + producto.cantidad
        dialogPrecioTextView.text = getString(R.string.Precio) + producto.precio
        dialogCategoriaTextView.text = getString(R.string.Categoria) + producto.categoria.joinToString(", ")
        dialogTiendaTextView.text = getString(R.string.Tienda) + producto.tiendas.joinToString(", ")


        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setPositiveButton(getString(R.string.cerrar)) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
    private fun cargarProductos() {
        inventoryList.clear()
        inventoryList.addAll(StorageUtil.obtenerListaProductos(this))
    }

    private fun guardarProductosEnAlmacenamiento() {
        StorageUtil.guardarListaProductos(this, inventoryList)
    }

    override fun onPause() {
        super.onPause()
        guardarProductosEnAlmacenamiento()
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
    private fun ordenarPorNombre() {
        inventoryList.sortBy { it.nombre }
        actualizarLista()
    }

    private fun ordenarPorCategoria() {
        inventoryList.sortBy { it.categoria.firstOrNull() ?: "" }
        actualizarLista()
    }

    private fun ordenarPorCantidad() {
        inventoryList.sortByDescending { it.cantidad }
        actualizarLista()
    }

    private fun actualizarLista() {
        adapter.notifyDataSetChanged()
    }
    private fun mostrarMenuOrdenamiento(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_ordenar, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.ordenar_nombre -> ordenarPorNombre()
                R.id.ordenar_categoria -> ordenarPorCategoria()
                R.id.ordenar_cantidad -> ordenarPorCantidad()
            }
            true
        }
        popupMenu.show()
    }
    private fun filtrarProductos(texto: String?) {
        val textoFiltrado = texto?.lowercase() ?: ""

        val listaFiltrada = inventoryList.filter {
            it.nombre.lowercase().contains(textoFiltrado) ||
                    it.categoria.any { categoria -> categoria.lowercase().contains(textoFiltrado) }
        }
        adapter = ProductoAdapter(listaFiltrada, this::mostrarDetalleProducto, this::eliminarProducto)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
    }
}