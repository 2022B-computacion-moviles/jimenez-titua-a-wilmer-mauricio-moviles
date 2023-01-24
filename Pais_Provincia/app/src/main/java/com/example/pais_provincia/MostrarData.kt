package com.example.pais_provincia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MostrarData : AppCompatActivity() {

    val gson= Gson()
    var paises= arrayListOf<Pais>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_data)
        mostrarData()

    }


    private fun mostrarData() {

        leerJson()

        var string = "************************Paises*********************************"
        paises.forEach {
            string += "\n---------------------------------------------------------------------------------\n${it.nombre_pais}\nCapital: ${it.capital_pais}," +
                    "CantHab: ${it.cantidad_habitantes_pais}, TM: ${it.tasa_mortalidad}, " +
                    "PaisCap: ${it.pais_capitalista} ,\nProvincias:\n "

            it.provincicas.forEach {
                string += "\n${it.nombre_provincia}, Capital: ${it.capital_provincia}, Fecha Fiesta Capital: ${it.fecha_fiesta_capital}," +
                        "Superfice: ${it.superficie_provincia}, Cantida Hab: ${it.cantidad_habitantes_provincia}\n"
            }

        }

        val mostrar = findViewById<TextView>(R.id.text_view_data)

        mostrar.text = string
    }

    private fun leerJson(){
        val archivo = InputStreamReader(openFileInput("myJson.json"))
        val br = BufferedReader(archivo)
        var linea = br.readLine()
        var administracion_pais=gson.fromJson(linea, Administracion::class.java)

        paises=administracion_pais.paises
    }
}