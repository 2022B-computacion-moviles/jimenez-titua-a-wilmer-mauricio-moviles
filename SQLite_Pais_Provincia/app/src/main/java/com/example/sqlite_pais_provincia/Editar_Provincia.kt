package com.example.sqlite_pais_provincia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.Button
import android.widget.EditText

class Editar_Provincia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pronvicia)

        val bundle=intent.extras


        val codigo_provincia=bundle?.getString("codigo_provincia")

        codigo_pais= bundle?.getString("id_pais").toString()
        nombre_pais=bundle?.getString("nombre_pais").toString()


        val nombre_provincia=findViewById<EditText>(R.id.input_nombre_provincia)
        val capital_provincia=findViewById<EditText>(R.id.input_capital_provincia)
        val cantidad_habitantes_provincia=findViewById<EditText>(R.id.input_cantidad_hab_provincia)
        val superficie_provincia=findViewById<EditText>(R.id.input_superficie)
        val fecha_fiesta_capital=findViewById<EditText>(R.id.input_fecha_fiesta_C)

        nombre_provincia.setText(bundle?.getString("nombre_provincia"))
        capital_provincia.setText(bundle?.getString("capital_provincia"))
        cantidad_habitantes_provincia.setText(bundle?.getString("cantidad_habitantes_provincia"))
        superficie_provincia.setText(bundle?.getString("superficie_provincia"))
        fecha_fiesta_capital.setText(bundle?.getString("fecha_fiesta_capital"))

        val botoneditarProvincia=findViewById<Button>(R.id.button_actualizar_provincia)
        botoneditarProvincia.setOnClickListener{
            var res = EBaseDeDatos.TablaPais!!.editarProvincia(
                codigo_provincia.toString().toInt(),
                nombre_provincia.text.toString(),
                capital_provincia.text.toString(),
                fecha_fiesta_capital.text.toString(),
                superficie_provincia.text.toString().toDouble(),
                cantidad_habitantes_provincia.text.toString().toInt()
            )

            abrirDialogo("Se ha actualizado correctamente")

        }

    }

    var codigo_pais=""
    var nombre_pais=""

    fun abrirDialogo(Titulo:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Titulo)
        builder.setPositiveButton(
            "Aceptar"
       ){_,_-> parametroID(Mostrar_Provincias::class.java)}

        val dialog = builder.create()
        dialog.show()
    }


    fun parametroID(
        clase: Class<*>
    ) {
        val intenProvinciasDePais=Intent(this,clase)
        intenProvinciasDePais.putExtra("codigo_pais",codigo_pais)
        intenProvinciasDePais.putExtra("nombre_pais",nombre_pais)
        startActivity(intenProvinciasDePais)
    }

}