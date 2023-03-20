package com.example.proyecto_iib

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Perfil : AppCompatActivity() {
    val db: FirebaseFirestore = Firebase.firestore
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val idUsuario =intent.getStringExtra("id")
        val tipo =intent.getStringExtra("tipo")
        val nombre =intent.getStringExtra("nombre")

        val agendar = findViewById<ImageButton>(R.id.btn_agendar)
        val cancelar = findViewById<ImageButton>(R.id.btn_cancelar)
        val home = findViewById<ImageButton>(R.id.btn_home)
        val pacientes = findViewById<ImageButton>(R.id.btn_pacientes)
        val tv_nombre = findViewById<EditText>(R.id.tv_nombre_perfil)
        val tv_email = findViewById<EditText>(R.id.tv_email_perfil)
        val btn_cambio = findViewById<Button>(R.id.btn_cambiar_contra)

        db.collection(tipo.toString())
            .document(idUsuario.toString())
            .get()
            .addOnSuccessListener { res ->
                if(res != null){
                    tv_nombre.setText("${res["nombre"]} ${res["apellido"]}")
                    tv_email.setText("${res["correo"]}")
                }
            }

        btn_cambio.setOnClickListener{
            abrirDialogoConfirmacion()
        }

        val btn_cerrar_Sesion=findViewById<Button>(R.id.btn_cerrar_sesion)
        btn_cerrar_Sesion.setOnClickListener{

            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
            val editor =prefs.edit()
            editor.clear()
            editor.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

            irActividad(MainActivity::class.java)

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
            home
                .setOnClickListener{
                    abrirActividad(Home::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }

        }else{
            pacientes
                .setOnClickListener{
                    abrirActividad(Pacientes::class.java,idUsuario.toString(),tipo.toString(),nombre.toString())
                }
        }

    }


    fun abrirDialogo(Titulo: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(Titulo)
        builder.setPositiveButton(
            "Aceptar",
            null
        )

        val dialog = builder.create()
        dialog.show()
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

    fun abrirDialogoConfirmacion( ){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro que quiere cambiar su contraseña?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog,
                    which->
                val tv_email = findViewById<EditText>(R.id.tv_email_perfil)
                restrablecerContraseña(tv_email.text.toString())
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun restrablecerContraseña(
        correo: String
    ){

        FirebaseAuth.getInstance().sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    abrirDialogo("Se ha enviado un correo de recuperación a su cuenta.")
                } else {
                    abrirDialogo("Error usuario no registrado")
                }
            }


    }

    override fun onBackPressed() {
        // Dejar vacío para deshabilitar el botón de retroceso
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }
}