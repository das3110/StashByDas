package com.example.stashbydas

import StorageUtil
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stashbydas.Clases.GlobalVariables
import com.example.stashbydas.Clases.Producto
import com.example.stashbydas.Clases.ProductoAdapter2

class Compras : AppCompatActivity() {
    private lateinit var etNombreProducto: EditText
    private lateinit var etCantidad: EditText
    private lateinit var etPrecio: EditText
    private lateinit var btnAgregarProducto: Button
    private lateinit var tvPrecioTotal: TextView
    private lateinit var tvSaldo: TextView
    private lateinit var spinnerCategorias: Spinner
    private lateinit var spinnerTiendas: Spinner
    
    private val categorias = listOf("Alimento", "Ropa", "Electronica","Otros")
    private val tiendas = listOf("Supermercado", "Farmacia", "Feria","Negocio")
    private val listaProductos = mutableListOf<Producto>()
    private lateinit var productoAdapter: ProductoAdapter2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)
        tvSaldo = findViewById(R.id.tvSaldo)
        etNombreProducto = findViewById(R.id.etNombreProducto)
        etCantidad = findViewById(R.id.etCantidad)
        etPrecio = findViewById(R.id.etPrecio)
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto)
        tvPrecioTotal = findViewById(R.id.tvPrecioTotal)
        etCantidad.addTextChangedListener(priceCalculator)
        etPrecio.addTextChangedListener(priceCalculator)
        spinnerCategorias = findViewById(R.id.spinnerCategorias)
        spinnerTiendas = findViewById(R.id.spinnerTiendas)
        val categoriaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        val tiendasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiendas)
        spinnerCategorias.adapter = categoriaAdapter
        spinnerTiendas.adapter = tiendasAdapter
        btnAgregarProducto.setOnClickListener {
            agregarProducto()
        }
        tvSaldo.text = getString(R.string.saldo, GlobalVariables.presupuesto.toString())
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProductos)
        productoAdapter = ProductoAdapter2(listaProductos) { position ->
            listaProductos.removeAt(position)
            productoAdapter.notifyDataSetChanged()
        }
        recyclerView.adapter = productoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val buttonComprarProductos: Button = findViewById(R.id.buttonComprarProductos)
        buttonComprarProductos.setOnClickListener {
            realizarCompra()

        }
    }

    private fun calcularPrecioTotalDeProductos(): Long {
        var precioTotal = 0L
        for (producto in listaProductos) {
            precioTotal += producto.precio * producto.cantidad
        }
        return precioTotal
    }
    private fun realizarCompra() {
        val precioTotal = calcularPrecioTotalDeProductos()
        if (precioTotal > GlobalVariables.presupuesto) {
            Toast.makeText(this, getString(R.string.saldo_insuficiente), Toast.LENGTH_SHORT).show()
        } else {
            GlobalVariables.presupuesto -= precioTotal
            GlobalVariables.productList.addAll(listaProductos)


            guardarProductosComprados()

            listaProductos.clear()
            productoAdapter.notifyDataSetChanged()
            clearFields()
            tvSaldo.text = getString(R.string.saldo, GlobalVariables.presupuesto.toString())
            tvPrecioTotal.text = getString(R.string.precio_total, "0")
        }
    }
    private val priceCalculator = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
        override fun afterTextChanged(s: Editable?) {
            val cantidad = etCantidad.text.toString().toLongOrNull() ?: 0L
            val precio = etPrecio.text.toString().toLongOrNull() ?: 0L
            val precioTotal = cantidad * precio
            tvPrecioTotal.text = getString(R.string.precio_total, precioTotal.toString())
        }
    }
    private fun agregarProducto() {
        val nombre = etNombreProducto.text.toString()
        val cantidad = etCantidad.text.toString().toLongOrNull() ?: 0L
        val precio = etPrecio.text.toString().toLongOrNull() ?: 0L
        val categoria = spinnerCategorias.selectedItem.toString()
        val tienda = spinnerTiendas.selectedItem.toString()
        val producto = Producto(nombre, cantidad.toInt(), precio, listOf(categoria), listOf(tienda))
        listaProductos.add(producto)

        productoAdapter.notifyDataSetChanged()
        clearFields()
    }
    private fun clearFields() {
        etNombreProducto.text.clear()
        etCantidad.text.clear()
        etPrecio.text.clear()
    }
    private fun guardarProductosComprados() {
        val productosComprados = StorageUtil.obtenerListaProductos(this) + listaProductos
        StorageUtil.guardarListaProductos(this, productosComprados)
    }
}