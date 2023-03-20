package com.example.proyecto_iib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue

class Registro_Odontologo : AppCompatActivity() {

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()



    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    private lateinit var telefono: EditText
    private lateinit var correo: EditText
    private lateinit var contrasenia: EditText
    private lateinit var dias_habiles: MutableList<String>
    private lateinit var btn_registrar: Button
    private lateinit var horaInicio: String
    private lateinit var horaFinal: String

    private lateinit var spinnerEspecialidades: Spinner



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_odontologo)

        val listaEspecialidad = ArrayList<Especialidad>()
        var especialidadEncontrada=Especialidad(0, "Seleccione la Especialidad")
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()


        nombre=findViewById<EditText>(R.id.txt_nombres_odontologo)
        apellido=findViewById<EditText>(R.id.txt_apellidos_odontologo)
        telefono=findViewById<EditText>(R.id.txt_telefono_odontologo)
        correo=findViewById<EditText>(R.id.txt_correo_odontologo)
        contrasenia=findViewById<EditText>(R.id.txt_contraseña_odontologo)
        btn_registrar=findViewById<Button>(R.id.btn_registrar_otontologo)


        db.collection("especialidades").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    especialidadEncontrada.cod_especialidad=document.id.toInt()
                    especialidadEncontrada.nombre_especialidad= document.data?.get("nombre").toString()
                    listaEspecialidad.add(especialidadEncontrada)
                    especialidadEncontrada=Especialidad(0, "Seleccione la Especialidad")
                }
                spinnerEspecialidades = findViewById(R.id.spinner_especialidades)

                val adaptador = Adaptador_Especialidades(this,listaEspecialidad)
                spinnerEspecialidades.adapter = adaptador

                spinnerEspecialidades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        var itemselect= spinnerEspecialidades.getItemAtPosition(position)
                        var especialidadSeleccionado= itemselect as Especialidad

                        btn_registrar.setOnClickListener {
                            //abrirDialogo("se aplasto")
                            dias_habiles_seleccionados()
                            registrarOdontologo(
                                nombre.text.toString(),
                                apellido.text.toString(),
                                telefono.text.toString().toInt(),
                                correo.text.toString(),
                                contrasenia.text.toString(),
                                especialidadSeleccionado.cod_especialidad.toString(),
                                dias_habiles,
                                 horaInicio,
                                horaFinal
                                )
                        }



                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //no se selecciono nada
                    }
                }


            }


        val spinnerHoraInicio=findViewById<Spinner>(R.id.spinner_hora_inicio)
        val horasInicio = arrayOf("07:00","08:00", "09:00", "10:00", "11:00", "12:00")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, horasInicio)
        spinnerHoraInicio.adapter = adapter
        spinnerHoraInicio.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                horaInicio=horasInicio[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }


        val spinnerHoraFinal=findViewById<Spinner>(R.id.spinner_hora_final)
        val horasFinal = arrayOf("14:00", "15:00", "16:00", "17:00", "18:00","19:00","20:00")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, horasFinal)
        spinnerHoraFinal.adapter = adapter1
        spinnerHoraFinal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                horaFinal=horasFinal[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



    }





    fun registrarOdontologo(
        nombre_odontologo: String,
        apellido_odontologo: String,
        telefono_odontologo: Int,
        correo_odontologo: String,
        contrasenia_odontologo: String,
        especialidad: String,
        dias_habiles: MutableList<String>,
        hora_atencion_inicio: String,
        hora_atencion_final: String
    ){

        //Servicio Authenticaction

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(correo_odontologo,contrasenia_odontologo)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    limpiarFormulario()
                    abrirDialogo("Se ha registrado exitosamente. Inicie Sesión")

                }else{
                    abrirDialogo("Se produjo un error en el registro.")

                }
            }

        val dato = hashMapOf(
            "nombre" to nombre_odontologo,
            "apellido" to apellido_odontologo,
            "telefono" to telefono_odontologo,
            "correo" to correo_odontologo,
            "contrasenia" to contrasenia_odontologo,
            "id_especialidad" to especialidad,
            "dias_habiles" to FieldValue.arrayUnion(*dias_habiles.toTypedArray()),
            "hora_atencion_inicio" to hora_atencion_inicio,
            "hora_atencion_final" to hora_atencion_final
        )

        db.collection("odontologos")
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

    fun dias_habiles_seleccionados(){

        val checkBox1 = findViewById<CheckBox>(R.id.dia_lunes)
        val checkBox2 = findViewById<CheckBox>(R.id.dia_martes)
        val checkBox3 = findViewById<CheckBox>(R.id.dia_miercoles)
        val checkBox4 = findViewById<CheckBox>(R.id.dia_jueves)
        val checkBox5 = findViewById<CheckBox>(R.id.dia_viernes)

        val miLista: MutableList<String> = mutableListOf()

            if (checkBox1.isChecked) {
                miLista.add("Lunes")
            }
            if (checkBox2.isChecked) {
                miLista.add("Martes")
            }
            if (checkBox3.isChecked) {
                miLista.add("Miercoles")
            }
            if (checkBox4.isChecked) {
                miLista.add("Jueves")
            }
            if (checkBox5.isChecked) {
                miLista.add("Viernes")
            }

        dias_habiles=miLista



    }






}