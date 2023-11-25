package com.example.stashbydas.Clases
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stashbydas.R

class UsuarioAdapter(
    context: Context,
    resource: Int,
    patients: List<Usuario>
) : ArrayAdapter<Usuario>(context, resource, patients) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val listItemView = convertView ?: inflater.inflate(R.layout.list_usuario, null)
        val usuario = getItem(position)

        val nameTextView = listItemView.findViewById<TextView>(R.id.textViewNombreProducto)
        val emailTextView = listItemView.findViewById<TextView>(R.id.textViewCantidadProducto)
        val telefonoTextView = listItemView.findViewById<TextView>(R.id.textViewTelefono)
        val presupuestoTextView=listItemView.findViewById<TextView>(R.id.textViewPlata)
        nameTextView.text = context.getString(R.string.nombre) + ": " + usuario?.nombre
        emailTextView.text = context.getString(R.string.email) + ": " + usuario?.email
        telefonoTextView.text = context.getString(R.string.telefono) + ": " + usuario?.telefono
        presupuestoTextView.text = context.getString(R.string.Presupuesto) + ": " + usuario?.presupuesto.toString()

        return listItemView
    }
}