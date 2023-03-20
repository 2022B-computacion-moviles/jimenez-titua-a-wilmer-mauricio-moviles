package com.example.proyecto_iib

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerCancelarCita(
    private val context: Context,
    val lista:ArrayList<Cita>,
    private val itemClickListener: OnCitaClickListener
):RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnCitaClickListener{
        fun onItemClick(idPaciente: String,fecha:String,hora:String)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return CitasViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cita,parent,false))
    }

    override fun getItemCount(): Int {
       return lista.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is CitasViewHolder -> holder.bind(lista[position],position)
            else -> throw IllegalAccessException("No funciona")
        }
    }

    inner class CitasViewHolder(itemView: View):BaseViewHolder<Cita>(itemView){
        override fun bind(item: Cita, position: Int) {
            val procedimiento: TextView = itemView.findViewById(R.id.tv_procedimiento)
            val fecha: TextView = itemView.findViewById(R.id.tv_fecha_cita)
            val hora: TextView = itemView.findViewById(R.id.tv_hora_cita)

            itemView.setOnClickListener { itemClickListener.onItemClick(
                item.idPaciente,
                item.fecha,
                item.hora
            ) }

            procedimiento.text = item.nombreProcedimiento
            fecha.text = item.fecha
            hora.text = item.hora
        }
    }
}