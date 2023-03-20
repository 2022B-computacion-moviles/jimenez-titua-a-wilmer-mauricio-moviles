package com.example.proyecto_iib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerPacientes (
    private val contexto:Context,
    private val lista: ArrayList<Paciente>): RecyclerView.Adapter<RecyclerPacientes.MyViewHolder>(){
    inner class MyViewHolder(view : View): RecyclerView.ViewHolder(view){
        val nombre: TextView
        val fecha: TextView

        init{
            nombre = view.findViewById(R.id.tv_procedimiento)
            fecha = view.findViewById(R.id.tv_fecha_cita)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(contexto)
            .inflate(
                R.layout.item_paciente,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var paciente = lista[position]
        holder.nombre.text = paciente.nombre
        holder.fecha.text = paciente.fechaUltimaCita

    }

    override fun getItemCount(): Int {
        return lista.size
    }
}