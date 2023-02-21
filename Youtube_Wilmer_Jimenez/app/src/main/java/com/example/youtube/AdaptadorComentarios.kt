package com.example.youtube


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdaptadorComentarios(
    private val contexto: Comentarios,
    private val lista: ArrayList<Comentario>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<AdaptadorComentarios.MyViewHolder>() {
        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

            var nombreComent: TextView
            var tiempoComent: TextView
            var fotoComent: ImageView
            var comentario: TextView
            var likesComentario: TextView
            var accionButtonLike: ImageButton

            var numerolikes=0

            init {
                nombreComent=view.findViewById(R.id.nombre_commetn)
                tiempoComent=view.findViewById(R.id.tiempo_coment)
                fotoComent=view.findViewById(R.id.perfil_Coment)
                comentario=view.findViewById(R.id.cotenido_comentario)
                likesComentario=view.findViewById(R.id.id_numero_likes_comentario)
                accionButtonLike=view.findViewById(R.id.btn_likes_comentario)
                accionButtonLike.setOnClickListener{
                    anadirLike()
                }
            }

            fun anadirLike(){
                numerolikes=numerolikes+1
                likesComentario.text=numerolikes.toString()

            }

        }


        //Setear el alyout que se va a utilizar
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_comentario,
                    parent,
                    false
                )
            return  MyViewHolder(itemView)
        }

        // setar datos para operacion
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val comentarioActual = this.lista[position]

           holder.nombreComent.text=comentarioActual.nombreUsComent
            holder.tiempoComent.text=comentarioActual.tiempoComent
            holder.comentario.text=comentarioActual.comentario

            val imagenUri: Uri = Uri.parse(comentarioActual.perfilComent)

            holder.fotoComent.setImageURI(imagenUri)
            holder.likesComentario.text="0"


        }
        // tamano arreglo
        override fun getItemCount(): Int {
            return  this.lista.size
        }

    }