package com.example.pais_provincia_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Switch


class Crear_Pais : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pais)

        val FirestoreData= FirestoreData()

        val nombre_pais = findViewById<EditText>(R.id.input_nombre_pais)
        val capital_pais = findViewById<EditText>(R.id.input_capital_pais)
        val cantidad_habitantes_pais = findViewById<EditText>(R.id.input_cantidad_hab_pais)
        val tasa_mort = findViewById<EditText>(R.id.input_tasa_mortalidad)
        val sw1 = findViewById<Switch>(R.id.switch_capitalista)

        var switchToF = 0
        sw1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchToF=1

            }
        }

        val botonCrearPais=findViewById<Button>(R.id.button_crear_pais)
        botonCrearPais
            .setOnClickListener {
                    FirestoreData.crearPais( nombre_pais.text.toString(),
                    capital_pais.text.toString(),
                    cantidad_habitantes_pais.text.toString().toInt(),
                    tasa_mort.text.toString().toDouble(),
                    switchToF)

                abrirDialogo("Se ha ingresado correctamente")
                limpiarEntradaTexto()
            }

    }

    fun abrirDialogo(Titulo:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Titulo)
        builder.setPositiveButton(
            "Aceptar",
            null
        )

        val dialog = builder.create()
        dialog.show()
    }

    private fun limpiarEntradaTexto() {

        val nombre_pais = findViewById<EditText>(R.id.input_nombre_pais)
        val capital_pais = findViewById<EditText>(R.id.input_capital_pais)
        val tasa_mort = findViewById<EditText>(R.id.input_tasa_mortalidad)
        val cantidad_hab = findViewById<EditText>(R.id.input_cantidad_hab_pais)
        nombre_pais.setText("")
        capital_pais.setText("")
        cantidad_hab.setText("")
        tasa_mort.setText("")
    }

}