package com.example.proyecto_iib

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Home : AppCompatActivity() {

    var citas: ArrayList<Cita> = arrayListOf<Cita>()
    val db: FirebaseFirestore = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val idUsuario =intent.getStringExtra("id")
        val tipo =intent.getStringExtra("tipo")
        val nombre =intent.getStringExtra("nombre")

        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)
        val perfil = findViewById<ImageButton>(R.id.btn_perfil)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_home)
        val sin_cita = findViewById<TextView>(R.id.sin_cita)
        val tv_nombre = findViewById<TextView>(R.id.tv_nombre)


        tv_nombre.setText("Bienvenido, "+nombre)

        //Conusltad de Citas paciente

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

        //Conusltad de Citas odontologo
        db.collection("citas")
            .whereEqualTo("idOdontologo",idUsuario)
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


        if(tipo == "pacientes"){
            agendar
                .setOnClickListener{
                    abrirActividad(Agendar::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }

            cancelar
                .setOnClickListener{
                    abrirActividad(Cancelar::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }

        }else{
            pacientes
                .setOnClickListener{
                    abrirActividad(Pacientes::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }
        }

        perfil
            .setOnClickListener{
                abrirActividad(Perfil::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
            }
    }

    fun inicializarRecyclerView(
        lista:ArrayList<Cita>,
        recyclerView: RecyclerView
    ){
        val adaptador = RecyclerCitas(
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

