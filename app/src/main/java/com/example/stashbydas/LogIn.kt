package com.example.stashbydas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.stashbydas.Clases.GlobalVariables
import com.example.stashbydas.Clases.Usuario
import com.example.stashbydas.Clases.UsuarioAdapter

class LogIn : AppCompatActivity() {
    private lateinit var usuario: MutableList<Usuario>

    private lateinit var listViewUsuarios: ListView
    private lateinit var adapterItem:UsuarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val buttonCrearUser = findViewById<Button>(R.id.buttonCrearUsuario)
        buttonCrearUser.setOnClickListener{
            showCrearUsuarioDialog()
        }
        listViewUsuarios = findViewById(R.id.listaUsuarios)
        usuario = mutableListOf(
            Usuario("Mama", "mama@example.com", "123456789", 1000000),
            Usuario("Papa", "papa@example.com", "987654321", 500000),
            Usuario("Hijo Mayor", "hijoMa@example.com", "123456799", 30000),
            Usuario("Hijo Menor", "hijoMe@example.com", "123456788", 0)
        )


        adapterItem = UsuarioAdapter(this, R.layout.list_usuario, usuario)
        listViewUsuarios.adapter = adapterItem

        listViewUsuarios.setOnItemClickListener { _, _, position, _ ->
            val selectedUsuario = usuario[position]
            GlobalVariables.userName = selectedUsuario.nombre
            
            if (selectedUsuario.presupuesto > 0) {
                GlobalVariables.presupuesto=selectedUsuario.presupuesto
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("usuario", selectedUsuario)
                startActivity(intent)
                finish()
            } else {
                val dialogView = layoutInflater.inflate(R.layout.dialog_presupuesto, null)
                val editTextMonto = dialogView.findViewById<EditText>(R.id.editTextMonto)
                val dialog = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setPositiveButton("Aceptar") { _, _ ->

                        val montoStr = editTextMonto.text.toString()
                        if (montoStr.isNotEmpty() && montoStr.matches(Regex("-?\\d+(\\.\\d+)?"))) {
                            val monto = montoStr.toLong()
                            if (monto > 0) {
                                GlobalVariables.presupuesto = monto
                                if (usuario[position] != null) {
                                    selectedUsuario.presupuesto = monto
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.putExtra("usuario", selectedUsuario)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                Toast.makeText(this, "El monto debe ser un número positivo", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Ingrese un número válido", Toast.LENGTH_SHORT).show()

                        }
                    }
                    .setNegativeButton("Cancelar") { _, _ ->
                    }
                    .create()

                dialog.show()
            }
        }
    }


    private fun showCrearUsuarioDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_crear_user, null)
        val editTextNombreUsuario = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextCorreoUsuario = dialogView.findViewById<EditText>(R.id.editTextEmail)
        val editTextTelefonoUsuario = dialogView.findViewById<EditText>(R.id.editTextCel)
        val editTextPlataUsuario = dialogView.findViewById<EditText>(R.id.editTextPlata)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Crear Nuevo Usuario")
            .setView(dialogView)
            .setPositiveButton("Crear") { _, _ ->
                val nombreUsuario = editTextNombreUsuario.text.toString()
                val correoUsuario = editTextCorreoUsuario.text.toString()
                val telefonoUsuario = editTextTelefonoUsuario.text.toString()
                val strplataUsuario = editTextPlataUsuario.text.toString()

                val plataUsuario=strplataUsuario.toLong()
                val nuevoUsuario = Usuario(nombreUsuario, correoUsuario,telefonoUsuario, plataUsuario)

                usuario.add(nuevoUsuario)
                adapterItem.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar") { _, _ ->
            }
            .create()

        dialog.show()
    }
}
