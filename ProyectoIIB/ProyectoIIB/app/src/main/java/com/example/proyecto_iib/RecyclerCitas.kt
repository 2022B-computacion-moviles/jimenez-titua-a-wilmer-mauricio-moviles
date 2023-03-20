package com.example.proyecto_iib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerCitas (
    private val contexto: Context,
    private val lista: ArrayList<Cita>): RecyclerView.Adapter<RecyclerCitas.MyViewHolder>(){
    inner class MyViewHolder(view : View): RecyclerView.ViewHolder(view){
        val procedimiento: TextView
        val fecha: TextView
        val hora: TextView

        init{
            procedimiento = view.findViewById(R.id.tv_procedimiento)
            fecha = view.findViewById(R.id.tv_fecha_cita)
            hora = view.findViewById(R.id.tv_hora_cita)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(contexto)
            .inflate(
                R.layout.item_cita,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var cita = lista[position]
        holder.procedimiento.text = cita.nombreProcedimiento
        holder.fecha.text = cita.fecha
        holder.hora.text = cita.hora

    }

    override fun getItemCount(): Int {
        return lista.size
    }
}