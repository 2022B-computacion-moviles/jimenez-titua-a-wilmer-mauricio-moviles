package com.example.sqlite_pais_provincia

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Switch

class Editar_Pais : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pais)

        val bundle=intent.extras



        val nombre_pais = findViewById<EditText>(R.id.input_nombre_pais)
        val capital_pais = findViewById<EditText>(R.id.input_capital_pais)
        val cantidad_habitantes_pais = findViewById<EditText>(R.id.input_cantidad_hab_pais)
        val tasa_mort = findViewById<EditText>(R.id.input_tasa_mortalidad)
        val sw1 = findViewById<Switch>(R.id.switch_capitalista)

        var capitalista=bundle?.getString("capitalista_pais")

        if(capitalista.toString().toInt()==1){
            sw1.setChecked(true)
        }



        var switchToF = 0


        sw1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                switchToF=1

            }
        }



        val codigo_pais=bundle?.getString("codigo_pais")



        nombre_pais.setText(bundle?.getString("nombre_pais"))
        capital_pais.setText(bundle?.getString("capital_pais"))
        cantidad_habitantes_pais.setText(bundle?.getString("cantidad_habitantes_pais"))
        tasa_mort.setText(bundle?.getString("tasa_mort"))


        val botoneditarPaiss=findViewById<Button>(R.id.button_actualizar_pais)
            botoneditarPaiss.setOnClickListener{
                        EBaseDeDatos.TablaPais!!.editarPais(
                        codigo_pais.toString().toInt(),
                        nombre_pais.text.toString(),
                        capital_pais.text.toString(),
                        cantidad_habitantes_pais.text.toString().toInt(),
                        tasa_mort.text.toString().toDouble(),switchToF)

                abrirDialogo("Se actualizado correctamente")

                }

            }


    fun abrirDialogo(Titulo:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Titulo)
        builder.setPositiveButton(
            "Aceptar"
            ){_,_-> irActividad(MainActivity::class.java)}
        val dialog = builder.create()
        dialog.show()
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }



    }
