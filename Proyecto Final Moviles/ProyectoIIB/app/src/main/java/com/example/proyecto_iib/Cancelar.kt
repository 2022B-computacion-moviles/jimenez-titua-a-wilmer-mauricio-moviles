package com.example.proyecto_iib

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Cancelar : AppCompatActivity(),RecyclerCancelarCita.OnCitaClickListener {
    var citas: ArrayList<Cita> = arrayListOf<Cita>()
    val db: FirebaseFirestore = Firebase.firestore
    var idUser = ""
    var type = ""
    var name = ""
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancelar)

        val idUsuario =intent.getStringExtra("id")
        idUser = idUsuario.toString()
        val tipo =intent.getStringExtra("tipo")
        val nombre =intent.getStringExtra("nombre")
        type = tipo.toString()
        name = nombre.toString()

        val home = findViewById<ImageButton>(R.id.btn_home)
        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)
        val perfil = findViewById<ImageButton>(R.id.btn_perfil)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_cancelar_cita)

        consultar(idUsuario.toString(),recyclerView)

        if(tipo == "pacientes"){
            agendar
                .setOnClickListener{
                    abrirActividad(Agendar::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }

            home
                .setOnClickListener{
                    abrirActividad(Home::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }

        }

        perfil
            .setOnClickListener{
                abrirActividad(Perfil::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
            }

    }

    override fun onItemClick(idPaciente: String, fecha: String, hora: String) {
        abrirDialogo(idPaciente,fecha,hora,type,name)
    }


    fun abrirDialogo(
        idPaciente: String,
        fecha: String,
        hora: String,
        tipo:String,
        nombre:String
    ){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro que quiere eliminar este elemento?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog,
                    which->

                db.collection("citas")
                    .whereEqualTo("idPaciente",idPaciente)
                    .whereEqualTo("fecha",fecha)
                    .whereEqualTo("hora",hora)
                    .get()
                    .addOnSuccessListener { res ->
                        for(cita in res)
                        db.collection("citas")
                            .document(cita.id)
                            .delete()
                    }
                abrirActividad(Cancelar::class.java,idUser,tipo,nombre)
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }


    private fun consultar(idUsuario:String,recyclerView: RecyclerView){
        db.collection("citas")
            .whereEqualTo("idPaciente",idUsuario)
            .get()
            .addOnSuccessListener{ resultado ->
                for ( cita  in resultado){
                    var nombreProd="Procedimiento"
                    db.collection("procedimientos")
                        .document(cita["idProcedimiento"].toString())
                        .get()
                        .addOnSuccessListener { doc ->
                            if(doc != null){
                                citas.add(
                                    Cita(
                                        cita["idPaciente"].toString(),
                                        cita["idOdontologo"].toString(),
                                        cita["idProcedimiento"].toString(),
                                        doc["nombre"].toString(),
                                        cita["hora"].toString(),
                                        cita["fecha"].toString(),
                                    )
                                )
                            }
                            inicializarRecyclerView(citas,recyclerView)
                        }
                }
            }
    }

    fun inicializarRecyclerView(
        lista:ArrayList<Cita>,
        recyclerView: RecyclerView
    ){
        val adaptador = RecyclerCancelarCita(
            this,
            lista,
            this
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        // Dejar vacío para deshabilitar el botón de retroceso
    }


    private fun abrirActividad(
        clase: Class<*>,
        id: String,
        tipo:String,
        nombre:String
    ) {
        val i = Intent(this, clase)
        i.putExtra("id", id)
        i.putExtra("tipo",tipo)
        i.putExtra("nombre",nombre)
        startActivity(i);
    }

}