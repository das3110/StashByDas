
package com.example.stashbydas.Clases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.stashbydas.R

class TiendasAdapter(
    context: Context,
    resource: Int,
    tiendas: List<Tiendas>
) : ArrayAdapter<Tiendas>(context, resource, tiendas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val listItemView = convertView ?: inflater.inflate(R.layout.lista_tiendas, null)
        val tienda = getItem(position)

        val nameTextView = listItemView.findViewById<TextView>(R.id.textViewNombreTienda)
        val btnEditar = listItemView.findViewById<Button>(R.id.buttonEditar) // Agrega el botón "Editar"

        nameTextView.text =  tienda?.nombre


        btnEditar.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("Editar Tienda")

            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.dialog_editar_tienda, null)
            alertDialogBuilder.setView(dialogView)

            val nombreEditText = dialogView.findViewById<EditText>(R.id.nombreEditText)
            val direccionEditText = dialogView.findViewById<EditText>(R.id.direccionEditText)
            val horarioEditText = dialogView.findViewById<EditText>(R.id.horarioEditText)
            val tiposPagoSpinner = dialogView.findViewById<Spinner>(R.id.tiposPagoSpinner)
            val categoriaSpinner = dialogView.findViewById<Spinner>(R.id.categoriaSpinner)
            val tiposPagoList = listOf("Efectivo", "Tarjetas ", "Efectivo y/o Tarjetas")
            val categoriaList = listOf("Farmacia", "Supermercado", "Feria", "Negocio")
            val tiposPagoAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, tiposPagoList)
            tiposPagoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tiposPagoSpinner.adapter = tiposPagoAdapter
            val categoriaAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, categoriaList)
            categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoriaSpinner.adapter = categoriaAdapter
            tiposPagoSpinner.setSelection(tiposPagoList.indexOfFirst { it == tienda?.tiposDePago?.firstOrNull() })
            categoriaSpinner.setSelection(categoriaList.indexOfFirst { it == tienda?.categoria?.firstOrNull() })

            val tienda = getItem(position)
            nombreEditText.setText(tienda?.nombre)
            direccionEditText.setText(tienda?.direccion)
            horarioEditText.setText(tienda?.horario)

            alertDialogBuilder.setPositiveButton("Guardar") { dialog, which ->
                // Manejar la lógica de guardar los cambios en la tienda
                val nuevoNombre = nombreEditText.text.toString()
                val nuevaDireccion = direccionEditText.text.toString()
                val nuevoHorario = horarioEditText.text.toString()

                // Actualizar la tienda con los nuevos valores
                tienda?.nombre = nuevoNombre
                tienda?.direccion = nuevaDireccion
                tienda?.horario = nuevoHorario
                tienda?.tiposDePago = listOf(tiposPagoList[tiposPagoSpinner.selectedItemPosition])
                tienda?.categoria = listOf(categoriaList[categoriaSpinner.selectedItemPosition])
                notifyDataSetChanged()
                dialog.dismiss()
            }

            alertDialogBuilder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        return listItemView
    }
}

