package com.example.stashbydas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleTiendas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_tiendas)

        val tiendaNombre = intent.getStringExtra("tienda_nombre")
        val tiendaDireccion = intent.getStringExtra("tienda-direccion")
        val tiendaHorario = intent.getStringExtra("tienda-horario")
        val tiendaPago = intent.getStringExtra("tienda-pago")
        val tiendaCategoria = intent.getStringExtra("tienda-categoria")

        val textViewNombre = findViewById<TextView>(R.id.textDetalleNombreTienda)
        val textViewDireccion = findViewById<TextView>(R.id.textDetalleDireccionTienda)
        val textViewHorario = findViewById<TextView>(R.id.textDetalleHorarioTienda)
        val textViewPago = findViewById<TextView>(R.id.textDetallePagoTienda)
        val textViewCategoria = findViewById<TextView>(R.id.textDetalleCategoriaTienda)

        textViewNombre.text = getString(R.string.nombre_tiendas, tiendaNombre)
        textViewDireccion.text = getString(R.string.direccion_tiendas, tiendaDireccion)
        textViewHorario.text = getString(R.string.horario_tiendas, tiendaHorario)
        textViewPago.text = getString(R.string.tipo_pago_tiendas, tiendaPago)
        textViewCategoria.text = getString(R.string.categoria_tiendas, tiendaCategoria)

        val btnEliminar = findViewById<Button>(R.id.buttonEliminarTienda)

        btnEliminar.setOnClickListener {
            val intent = Intent()
            intent.putExtra("tienda_eliminada_nombre", tiendaNombre)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

}
