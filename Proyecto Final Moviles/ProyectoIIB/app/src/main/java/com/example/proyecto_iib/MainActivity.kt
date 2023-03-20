package com.example.proyecto_iib

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        comprobarSesionActiva()

        val boton_ir_iniciar_sesion=findViewById<Button>(R.id.btn_ir_iniciar_sesion)
        boton_ir_iniciar_sesion.setOnClickListener {
            irActividad(Inicio_Sesion::class.java)

        }
        val ir_registro= findViewById<TextView>(R.id.text_ir_registrarse)
        ir_registro.setOnClickListener {

            irActividad(Registro_Paciente::class.java)

        }
    }


    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }

    private fun comprobarSesionActiva() {

        val prefs=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val correo=prefs.getString("correo",null)
        val nombre=prefs.getString("nombre",null)

        if(correo!=null && nombre!= null){

            db.collection("odontologos").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (correo == document.data?.get("correo")
                                .toString()
                        ){
                            nombreUsuarioLogueado(Pacientes::class.java, document.data?.get("nombre").toString()
                                ,document.data?.get("correo").toString())
                        }

                    }
                }

            db.collection("pacientes").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (correo == document.data?.get("correo")
                                .toString()
                        ){
                            nombreUsuarioLogueado(Calendario_Citas::class.java, document.data?.get("nombre").toString()
                                ,document.data?.get("correo").toString())
                        }

                    }
                }


        }


    }

    fun nombreUsuarioLogueado(
        clase: Class<*>,
        nombre: String,
        correo:String
    ) {
        val usuario = Intent(this, clase)
        usuario.putExtra("nombre_us", nombre)
        usuario.putExtra("correo",correo)
        startActivity(usuario)
    }
}