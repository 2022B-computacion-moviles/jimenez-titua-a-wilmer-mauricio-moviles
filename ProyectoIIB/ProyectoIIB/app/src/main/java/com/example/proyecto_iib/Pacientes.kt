package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Pacientes : AppCompatActivity() {
    private var listaPacientes: ArrayList<Paciente> = arrayListOf()
    val db: FirebaseFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes)

        val idUsuario =intent.getStringExtra("id")
        val tipo =intent.getStringExtra("tipo")
        val nombre =intent.getStringExtra("nombre")

        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val home = findViewById<ImageButton>(R.id.btn_home)
        val perfil = findViewById<ImageButton>(R.id.btn_perfil)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_pacientes)

        db.collection("citas")
            .whereEqualTo("idOdontologo",idUsuario)
            .get()
            .addOnSuccessListener{ resultado ->
                for ( cita  in resultado){
                    db.collection("procedimientos")
                        .document(cita["idProcedimiento"].toString())
                        .get()
                        .addOnSuccessListener { doc ->
                            if(doc != null){
                                db.collection("pacientes")
                                    .document(cita["idPaciente"].toString())
                                    .get()
                                    .addOnSuccessListener { res ->
                                        if(res !=null)
                                            listaPacientes.add(
                                                Paciente(
                                                    res["nombre"].toString()+res["apellido"].toString(),
                                                    doc["nombre"].toString(),
                                                ))
                                        inicializarRecyclerView(listaPacientes,recyclerView)
                                    }
                            }
                        }
                }
            }

        /*listaPacientes.add(Paciente("Henry Tumailli","02/02/2023"))
        listaPacientes.add(Paciente("Henry Tumailli","02/02/2023"))
        listaPacientes.add(Paciente("Henry Tumaill","02/02/2023"))*/




        home
                .setOnClickListener{
                    abrirActividad(Home::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }

        perfil
            .setOnClickListener{
                abrirActividad(Perfil::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
            }
    }

    fun inicializarRecyclerView(
        lista:ArrayList<Paciente>,
        recyclerView: RecyclerView
    ){
        val adaptador = RecyclerPacientes(
            this,
            lista
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