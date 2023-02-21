package com.example.youtube

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.concurrent.timer

class AdaptadorVideosView (
    private val contexto: MainActivity,
    private val lista: ArrayList<Video>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AdaptadorVideosView.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val videoViews: VideoView
        val imagen: ImageView
        val nombreCanal: TextView
        val descripcionVideo: TextView
        val imagenPerfilCircular: ShapeableImageView
        var botonComentarios: ImageButton
        val likesTextView: TextView
        val accionButton: ImageButton

        val iconoPause: ImageView
        val iconoPlay: ImageView
        val textShort: TextView
        val iconoSusc: ImageView
        val textoSus: TextView

        val bottomSheetFragment=Comentarios()

        val mediaController = MediaController(contexto)
        var numeroLikes = 0


        val progressBar: ProgressBar
        init {
            videoViews=view.findViewById(R.id.id_video_view)
            imagen=view.findViewById(R.id.id_imagenPerfil_c)
            imagenPerfilCircular=view.findViewById(R.id.id_perfil_circular)
            nombreCanal=view.findViewById(R.id.nombre_canal)
            descripcionVideo=view.findViewById(R.id.descripcionVideo)
            progressBar=view.findViewById(R.id.progressBar)
            botonComentarios=view.findViewById(R.id.btn_comentarios)
            likesTextView = view.findViewById(R.id.tv_likes)
            accionButton=view.findViewById(R.id.btn_like_video)
            accionButton.setOnClickListener {
                anadirLike()
                }

                mediaController.setAnchorView(videoViews)

            iconoPause=view.findViewById(R.id.id_icono_pause)
            iconoPlay=view.findViewById(R.id.id_icono_play)
            textShort=view.findViewById(R.id.id_text_short)
            iconoSusc=view.findViewById(R.id.id_icono_suscripciones)
            textoSus=view.findViewById(R.id.id_text_suscripciones)

        }

        fun anadirLike(){

            numeroLikes =  numeroLikes + 1
            likesTextView.text = numeroLikes.toString()

        }





    }




    //Setear el alyout que se va a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.videosview,
                parent,
                false
            )
        return  MyViewHolder(itemView)
    }

    // setar datos para operacion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val shortActual = this.lista[position]



        holder.nombreCanal.text = shortActual.nombreCanal
        holder.descripcionVideo.text=shortActual.descripcionVideo


        val imagenUri: Uri = Uri.parse(shortActual.imagenPerfil)
        val onlineUri : Uri = Uri.parse(shortActual.URL)
        var tiempoduracion=0



        holder.imagen.setImageURI(imagenUri)
        holder.imagenPerfilCircular.setImageURI(imagenUri)
        holder.videoViews.setVideoURI(onlineUri)
        holder.progressBar.max=800

        holder.videoViews.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            holder.videoViews.start()
            val currentProgress=800
            ObjectAnimator.ofInt(holder.progressBar, "progress",currentProgress)
                .setDuration(10000)
                .start()


        })
        holder.videoViews.setOnPreparedListener(MediaPlayer.OnPreparedListener {


            tiempoduracion= holder.videoViews.duration
            val currentProgress=800
            ObjectAnimator.ofInt(holder.progressBar, "progress",currentProgress)
                .setDuration(tiempoduracion.toLong())
                .start()
            holder.videoViews.start()

        })

        //Controlar la pause del video
        holder.videoViews.setOnClickListener {

            if(holder.videoViews.isPlaying==true){
                holder.videoViews.pause()

                holder.textoSus.visibility=View.VISIBLE
                holder.textShort.visibility=View.VISIBLE
                holder.iconoSusc.visibility=View.VISIBLE

                val animation =AnimationUtils.loadAnimation(contexto,R.anim.fade_in)
                holder.iconoPause.startAnimation(animation)
                val animation1 =AnimationUtils.loadAnimation(contexto,R.anim.fade_out)
                holder.iconoPause.startAnimation(animation1)


            }else{
                holder.iconoPause.visibility=View.INVISIBLE
                holder.videoViews.start()

                holder.textoSus.visibility=View.INVISIBLE
                holder.textShort.visibility=View.INVISIBLE
                holder.iconoSusc.visibility=View.INVISIBLE

                val animation =AnimationUtils.loadAnimation(contexto,R.anim.fade_in)
                holder.iconoPlay.startAnimation(animation)
                val animation1 =AnimationUtils.loadAnimation(contexto,R.anim.fade_out)
                holder.iconoPlay.startAnimation(animation1)

            }

        }


       //Recycler comentarios
        holder.botonComentarios.setOnClickListener {



        holder.bottomSheetFragment.show(contexto.supportFragmentManager, "BottomSheetDialog")

        }
        holder.likesTextView.text="0"

    }

    // tamano arreglo
    override fun getItemCount(): Int {
        return  this.lista.size
    }

}

