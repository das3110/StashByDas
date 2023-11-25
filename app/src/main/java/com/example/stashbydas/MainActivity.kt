package com.example.stashbydas

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.stashbydas.Clases.GlobalVariables
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        aplicarIdiomaGuardado()
        val buttonComprar = findViewById<Button>(R.id.button2)
        val buttonAlmacen =findViewById<Button>(R.id.buttonInventario)
        val buttonTiendas = findViewById<Button>(R.id.button3)
        val buttonConfiguracion = findViewById<Button>(R.id.button4)
        val buttonCerrarSesion = findViewById<Button>(R.id.button5)
        val buttonNotas = findViewById<ImageButton>(R.id.imageButton2)
        val user= findViewById<TextView>(R.id.textViewUsuario)
        user.setText(GlobalVariables.userName)
        val plata= findViewById<TextView>(R.id.textViewMonto)
        plata.setText(GlobalVariables.presupuesto.toString())

        buttonNotas.setOnClickListener {
            val intent= Intent(this,Notas::class.java )
            startActivity(intent)
        }
        buttonComprar.setOnClickListener {
            val intent= Intent(this,Compras::class.java )
            startActivity(intent)
        }
        buttonAlmacen.setOnClickListener{
            val intent= Intent(this,Inventario::class.java )
            startActivity(intent)
        }
        buttonTiendas.setOnClickListener {
            val intent= Intent(this,TiendasFrecuentes::class.java )
            startActivity(intent)
        }
        buttonConfiguracion.setOnClickListener{
            val intent= Intent(this,Configuracion::class.java )
            startActivity(intent)
        }
        buttonCerrarSesion.setOnClickListener{
            val intent= Intent(this,LogIn::class.java )
            startActivity(intent)
            GlobalVariables.presupuesto=0
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
        val plata = findViewById<TextView>(R.id.textViewMonto)
        plata.text = GlobalVariables.presupuesto.toString()
    }
    private fun aplicarIdiomaGuardado() {
        val sharedPreferences = getSharedPreferences("Preferencias", MODE_PRIVATE)
        val idioma = sharedPreferences.getString("IdiomaSeleccionado", "es")
        cambiarIdioma(idioma)
    }
    private fun cambiarIdioma(codigoIdioma: String?) {
        codigoIdioma?.let {
            val locale = Locale(it)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        }
    }
}
