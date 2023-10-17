package com.example.stashbydas

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.example.stashbydas.Clases.GlobalVariables

class Configuracion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)
        val buttonVolver = findViewById<Button>(R.id.buttonVolverConfig)
        val switch = findViewById<Switch>(R.id.switch1)
        val sharedPreferences = getSharedPreferences("Mode", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val nightMode = sharedPreferences.getBoolean("night", false)
        if (nightMode) {
            switch.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        switch.setOnCheckedChangeListener { buttonview, isChecked ->
            if (!isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("night", false)
                editor.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("night", true)
                editor.apply()
            }
        }
        buttonVolver.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java )
            startActivity(intent)
            finish()
        }
        val botonsplash = findViewById<Button>(R.id.button7)
        botonsplash.setOnClickListener {
            val listTimes = arrayOf("1", "2", "3", "4", "5")

            val xBuilder = AlertDialog.Builder(this)
            xBuilder.setTitle("Seleccione el tiempo deseado")
            xBuilder.setSingleChoiceItems(listTimes, -1) { dialog, which ->
                when (which) {
                    0 -> actualizarTiempo(1000)
                    1 -> actualizarTiempo(2000)
                    2 -> actualizarTiempo(3000)
                    3 -> actualizarTiempo(4000)
                    4 -> actualizarTiempo(5000)
                }
                dialog.dismiss()
                recreate()
            }

            val xDialog = xBuilder.create()
            xDialog.show()
        }

    }
}
private fun actualizarTiempo(dato:Long ){
    GlobalVariables.splashTime= dato
}