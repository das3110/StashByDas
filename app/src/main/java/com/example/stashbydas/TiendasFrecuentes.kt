package com.example.stashbydas


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.stashbydas.Clases.Tiendas
import com.example.stashbydas.Clases.TiendasAdapter



private lateinit var tiendas: MutableList<Tiendas>
private lateinit var listViewTiendas: ListView
private lateinit var adapterItem: TiendasAdapter
private lateinit var tiposPagoAdapter: ArrayAdapter<String>
private lateinit var categoriaAdapter: ArrayAdapter<String>
class TiendasFrecuentes : AppCompatActivity() {
    companion object {
        private const val REQUEST_ELIMINAR_TIENDA = 1

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiendas_frecuentes)
        val btnAgregarTienda = findViewById<Button>(R.id.buttonAgregarTienda)
        btnAgregarTienda.setOnClickListener {
            showCrearTiendaDialog()
        }
        listViewTiendas = findViewById(R.id.listaTiendas)
        tiendas = mutableListOf(
            Tiendas("Cruz Verde", "Mall Florida", "08:00 a 20:00", listOf("Efectivo"), listOf("Farmacia") ),
            Tiendas("Tottus", "Mall Florida", "09:00 a 20:30", listOf("Efectivo y/o Tarjetas"), listOf("Supermercado") ),
            Tiendas("Lider", "Colin", "09:00 a 21:00", listOf("Efectivo y/o Tarjetas"), listOf("Supermercado") ),
            Tiendas("Feria Dominical", "20 sur", "08:00 a 12:00", listOf("Efectivo"), listOf("Feria") ),
            Tiendas("Almacen Carlitos", "23 sur", "10:00 a 19:00", listOf("Efectivo y/o Tarjetas"), listOf("Negocio") )
        )


        adapterItem = TiendasAdapter(this, R.layout.lista_tiendas, tiendas)
        listViewTiendas.adapter = adapterItem

        listViewTiendas.setOnItemClickListener { _, _, position, _ ->

            val selectedTienda = tiendas[position]
            val intent = Intent(this, DetalleTiendas::class.java)
            intent.putExtra("tienda_nombre", selectedTienda.nombre)
            intent.putExtra("tienda-direccion",selectedTienda.direccion)
            intent.putExtra("tienda-horario",selectedTienda.horario)
            intent.putExtra("tienda-pago",selectedTienda.tiposDePago.toString())
            intent.putExtra("tienda-categoria",selectedTienda.categoria.toString())

            startActivityForResult(intent, REQUEST_ELIMINAR_TIENDA)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ELIMINAR_TIENDA && resultCode == RESULT_OK) {
            val tiendaEliminadaNombre = data?.getStringExtra("tienda_eliminada_nombre")
            tiendas.removeIf { it.nombre == tiendaEliminadaNombre }
            adapterItem.notifyDataSetChanged()
        }
    }

    private fun showCrearTiendaDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_crear_tienda, null)

        val nombreEditText = dialogView.findViewById<EditText>(R.id.nombreEditText)
        val direccionEditText = dialogView.findViewById<EditText>(R.id.direccionEditText)
        val horarioEditText = dialogView.findViewById<EditText>(R.id.horarioEditText)
        val tiposPagoSpinner = dialogView.findViewById<Spinner>(R.id.tiposPagoSpinner)
        val categoriaSpinner = dialogView.findViewById<Spinner>(R.id.categoriaSpinner)
        tiposPagoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("Efectivo", "Tarjetas", "Efectivo y/o Tarjetas"))
        categoriaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("Farmacia", "Supermercado", "Negocio","Feria"))

        tiposPagoSpinner.adapter = tiposPagoAdapter
        categoriaSpinner.adapter = categoriaAdapter

        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.agregarnuevatienda))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.crear)) { _, _ ->
                val nombreTienda = nombreEditText.text.toString()
                val direccionTienda = direccionEditText.text.toString()
                val horarioTienda = horarioEditText.text.toString()
                val pagosTienda = tiposPagoSpinner.selectedItem.toString()
                val categoriasTienda = categoriaSpinner.selectedItem.toString()

                val nuevaTienda = Tiendas(nombreTienda, direccionTienda,horarioTienda, listOf(pagosTienda),
                    listOf(categoriasTienda))

                tiendas.add(nuevaTienda)
                adapterItem.notifyDataSetChanged()
            }
            .setNegativeButton(getString(R.string.cancelar)) { _, _ ->
            }
            .create()

        dialog.show()
    }

}

