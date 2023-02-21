package com.example.youtube


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import androidx.recyclerview.widget.RecyclerView

import kotlin.random.Random

class Comentarios : BottomSheetDialogFragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.activity_comentarios, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var numComent =view.findViewById<TextView>(R.id.id_num_coment)


        val listaComentarios= arrayListOf<Comentario>()

        listaComentarios.add(Comentario("@cesar silva","• hace 1 año.","android.resource://${context?.packageName}/${R.drawable.cperfil1}","Los mejores expertos informaticos recomiendan comprar mas liquido cuando se acaba."))
        listaComentarios.add(Comentario("@TheYoanZ","• hace 1 año.","android.resource://${context?.packageName}/${R.drawable.cperfil2}","No recuerdo en que canal lo vi pero, el líquido disipa bien la temperatura los primeros minutos de encender la pc. Pero luego de que el líquido iguala la temperatura del ssd, actúa como un disipador pasivo comun."))
        listaComentarios.add(Comentario("@Emanuel H.A","• hace 5 m.","android.resource://${context?.packageName}/${R.drawable.cperfil3}","No considero necesaria una refrigeración líquida en un NVMe, me parece mejor el uso de un disipador pasivo."))
        listaComentarios.add(Comentario("@FalvarezD","• hace 2 sem.","android.resource://${context?.packageName}/${R.drawable.cperfil4}","Tengo 2 969evo de una tb cada uno con un uso intenso y sin disipador , cómo informático te diré que no necesitan disipar y solo pagas por algo que no necesitas"))
        listaComentarios.add(Comentario("@Martin Pareja","• hace 1 año.","android.resource://${context?.packageName}/${R.drawable.cperfil5}","Mil veces prefiero los ssd normales, Ahí se gasta más y te hace dar más trabajo ese tipo de Ssd"))
        listaComentarios.add(Comentario("@Sandro Aimar","• hace 2 sem.","android.resource://${context?.packageName}/${R.drawable.cperfil6}","creo que ya no se venden, o se vende poco porque es toda una inversion lo del liquido y creo yo que no esta barato"))
        listaComentarios.add(Comentario("@bryan smith","• hace 1 año.","android.resource://${context?.packageName}/${R.drawable.cperfil7}","Cuando se acabe, le echas refrigerante para carros y ya. Tuve una RL y eso le echaba, siempre mantenía muy buena temperatura"))


        val start = 0
        val end = 6
        var objetos= arrayListOf<Comentario>()

        val start_=7
        val end_=50
        var num=rand(start_,end_)

        numComent.text=num.toString()

        for (i in 1..num)objetos.add(listaComentarios[rand(start, end)])
        
        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerview_comentarios)

        inicializarRecyclerView(objetos,recyclerView)




    }

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }

    private fun inicializarRecyclerView(
        listaComentarios: ArrayList<Comentario>,
        recyclerView: RecyclerView)

    {

        val adaptador = AdaptadorComentarios(
            this,
            listaComentarios,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        adaptador.notifyDataSetChanged()

    }






}