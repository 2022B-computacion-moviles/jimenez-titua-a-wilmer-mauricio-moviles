package com.example.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {



    var totalLikes = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val listaVideos= arrayListOf<Video>()

        listaVideos.add(Video("@Viccio Tech","Este SSD tiene LIQUIDO #hardware #pcgamer #pc","android.resource://$packageName/${R.drawable.perfil1}","android.resource://$packageName/${R.raw.video1}"))
        listaVideos.add(Video("@programacionymas","Sé bueno! Dale un nombre atus argumentos","android.resource://$packageName/${R.drawable.perfil2}","android.resource://$packageName/${R.raw.video2}"))
        listaVideos.add(Video("@makigas","Intercambio de variable con animaciones","android.resource://$packageName/${R.drawable.perfil3}","android.resource://$packageName/${R.raw.video3}"))
        listaVideos.add(Video("@CodingTube","El mejor TEMA para Visual Studio Code #Shorts","android.resource://$packageName/${R.drawable.perfil4}","android.resource://$packageName/${R.raw.video4}"))




        val recyclerView = findViewById<RecyclerView>(R.id.rv_entrenadores)
        inicializarRecyclerView(listaVideos,recyclerView)



    }
    fun inicializarRecyclerView(
        lista:ArrayList<Video>,
        recyclerView: RecyclerView
    ){
        val adaptador = AdaptadorVideosView(
            this,
            lista,
            recyclerView
        )

        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }




}

