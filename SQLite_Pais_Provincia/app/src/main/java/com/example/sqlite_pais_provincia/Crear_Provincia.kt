package com.example.sqlite_pais_provincia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.Button
import android.widget.EditText

class Crear_Provincia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_provincia)

        val bundle=intent.extras
        val codigo_pais=bundle?.getString("codigo_pais")


        val nombre_provincia=findViewById<EditText>(R.id.input_nombre_provincia)
        val capital_provincia=findViewById<EditText>(R.id.input_capital_provincia)
        val cantidad_habitantes_provincia=findViewById<EditText>(R.id.input_cantidad_hab_provincia)
        val superfice_provincia=findViewById<EditText>(R.id.input_superficie)
        val fecha_fiesta_capital=findViewById<EditText>(R.id.input_fecha_fiesta_C)



        val crearProvincia=findViewById<Button>(R.id.button_crear_provincia)
        crearProvincia.setOnClickListener {
            EBaseDeDatos.TablaPais!!.crearProvincia(
                nombre_provincia.text.toString()
                ,capital_provincia.text.toString(),
                fecha_fiesta_capital.text.toString(),
                superfice_provincia.text.toString().toDouble(),
                cantidad_habitantes_provincia.text.toString().toInt(),
                codigo_pais.toString().toInt()  )
            abrirDialogo("Se ha ingresado correctamente")
            limpiarEntradaTextoP()
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



    private fun limpiarEntradaTextoP() {


        val nombre_provincia = findViewById<EditText>(R.id.input_nombre_provincia)
        val capital_provincia = findViewById<EditText>(R.id.input_capital_provincia)
        val superficie_provincia = findViewById<EditText>(R.id.input_superficie)
        val cantidad_hab = findViewById<EditText>(R.id.input_cantidad_hab_provincia)
        val fecha = findViewById<EditText>(R.id.input_fecha_fiesta_C)

        nombre_provincia.setText("")
        capital_provincia.setText("")
        superficie_provincia.setText("")
        cantidad_hab.setText("")
        fecha.setText("")

    }
}