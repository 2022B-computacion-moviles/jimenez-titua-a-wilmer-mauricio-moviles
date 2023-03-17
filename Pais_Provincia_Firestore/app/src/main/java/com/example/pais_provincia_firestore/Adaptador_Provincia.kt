package com.example.pais_provincia_firestore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Adaptador_Provincia(
    mContext: Context,
    datos: ArrayList<Provincia>,

    ):BaseAdapter() {

    val datosProvincia=datos
    val ctx=mContext


    inner class ViewHolder(){
        var nombre: TextView?=null
    }

    override fun getView(position: Int, row: View?, parent: ViewGroup?): View? {

        var rowview=row
        var viewHolder: ViewHolder

        if(rowview==null){
            viewHolder=ViewHolder()
            val inflater=LayoutInflater.from(ctx)
            rowview=inflater.inflate(R.layout.item_provincia,parent,false)

            viewHolder.nombre=rowview.findViewById(R.id.row_nombre) as TextView
            rowview.tag=viewHolder
        }else{
            viewHolder=rowview.tag as ViewHolder
        }
        viewHolder.nombre!!.setText(datosProvincia.get(position).nombre_provincia)

        return rowview


    }
    override fun getCount(): Int {
        return datosProvincia.size
    }

    override fun getItem(p0: Int): Any {
        return datosProvincia[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }





}