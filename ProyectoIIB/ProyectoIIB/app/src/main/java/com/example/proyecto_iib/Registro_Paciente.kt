package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registro_Paciente : AppCompatActivity() {

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var telefono: EditText
    private lateinit var correo: EditText
    private lateinit var contrasenia: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resgistro_paciente)

        val ir_registro_odontologo= findViewById<TextView>(R.id.text_ir_registro_odont)
        ir_registro_odontologo.setOnClickListener {

            irActividad(Registro_Odontologo::class.java)

        }

        nombre=findViewById<EditText>(R.id.txt_nombres_paciente)
        apellido=findViewById<EditText>(R.id.txt_apellidos_paciente)
        telefono=findViewById<EditText>(R.id.txt_telefono_paciente)
        correo=findViewById<EditText>(R.id.txt_correo_paciente)
        contrasenia=findViewById<EditText>(R.id.txt_contraseña_paciente)

        val btn_registrar=findViewById<Button>(R.id.btn_registrar_paciente)

        btn_registrar.setOnClickListener{
            registrarPaciente(
                nombre.text.toString(),
                apellido.text.toString(),
                telefono.text.toString().toInt(),
                correo.text.toString(),
                contrasenia.text.toString())
        }

    }


    fun registrarPaciente(
        nombre_paciente: String,
        apellido_paciente: String,
        telefono_paciente: Int,
        correo_paciente: String,
        contrasenia_paciente: String

        ) {

        //Servicio Authenticaction

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(correo_paciente,contrasenia_paciente)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    limpiarFormulario()
                    abrirDialogo("Se ha registrado exitosamente. Inicie Sesión")

                }else{
                    abrirDialogo("Se produjo un error en el registro.")

                }
            }

            val dato = hashMapOf(
                "nombre" to nombre_paciente,
                "apellido" to apellido_paciente,
                "telefono" to telefono_paciente,
                "correo" to correo_paciente,
                "contrasenia" to contrasenia_paciente
            )

            db.collection("pacientes")
                .add(dato)
                .addOnSuccessListener {
                    Log.d("Authenticaction", "Ok")

                }
                .addOnFailureListener {
                    Log.d("Authenticaction", "Error")

                }

        }

    fun abrirDialogo(Titulo:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Titulo)
        builder.setPositiveButton(
            "Aceptar"
        ) { _, _ -> irActividad(Inicio_Sesion::class.java) }

        val dialog = builder.create()
        dialog.show()
    }

    fun limpiarFormulario(){
        nombre.setText("")
        apellido.setText("")
        telefono.setText("")
        correo.setText("")
        contrasenia.setText("")

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }




    }


