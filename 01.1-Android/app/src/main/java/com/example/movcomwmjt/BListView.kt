package com.example.movcomwmjt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListView : AppCompatActivity() {

    val arreglo: ArrayList<BEntrenador> = BBaseDatosMemoria.arregloBEntrenador


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listView=findViewById<ListView>(R.id.lv_list_view)
        val adaptador= ArrayAdapter(
            this, // Contexto
        android.R.layout.simple_list_item_1, // como se va a ver (XML)
        arreglo
        )
        listView.adapter=adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView= findViewById<Button>(
            R.id.btn_anadir_list_view
        )

        botonAnadirListView
            .setOnClickListener{
                anadirEntrenador(adaptador)
            }
    }

    fun anadirEntrenador(adaptador: ArrayAdapter<BEntrenador>){
        arreglo.add(
            BEntrenador(
                "Ejemplo", "a@a.com"
            )
        )

    }
}