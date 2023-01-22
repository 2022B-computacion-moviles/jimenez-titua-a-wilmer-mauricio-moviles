package com.example.pais_provincia

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : AppCompatActivity() {
    var paises = arrayListOf<Pais>()
    val provincias = arrayListOf<Provincia>()
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (fileList().contains("myJson.json")) {
            showToast("Archivo Cargado")
            leerJson()
        } else {
            guardarJson(paises)
            showToast("Archivo Creado")
            leerJson()
        }
        //Pais
        val cod_pais = findViewById<EditText>(R.id.cod_pais)
        val nombre_pais = findViewById<EditText>(R.id.nombre_pais)
        val sw1 = findViewById<Switch>(R.id.pais_capitalista)
        val tasa_mort = findViewById<EditText>(R.id.tasa_mortalidad)
        val cantidad_habitantes_pais = findViewById<EditText>(R.id.cantidad_habitantes_pais)
        val capital_pais = findViewById<EditText>(R.id.capital_pais)

        val ingresarPais = findViewById<Button>(R.id.ingresarPais)

        ingresarPais.setOnClickListener {
            ingresarPais(
                cod_pais,
                tasa_mort,
                nombre_pais,
                sw1,
                cantidad_habitantes_pais,
                capital_pais
            )
        }

        val mostrar = findViewById<Button>(R.id.mostrarDatos)

        mostrar.setOnClickListener {
            irActividad(MostrarData::class.java)

        }


        val buscar = findViewById<Button>(R.id.btnBuscar)

        buscar.setOnClickListener {

            buscarPais(
                cod_pais,
                tasa_mort,
                nombre_pais,
                sw1,
                cantidad_habitantes_pais,
                capital_pais
            )

        }


        val actualizar = findViewById<Button>(R.id.btnactualizar)

        actualizar.setOnClickListener {
            actualizarPais(
                cod_pais,
                tasa_mort,
                nombre_pais,
                sw1,
                cantidad_habitantes_pais,
                capital_pais
            )
        }

        val eliminar = findViewById<Button>(R.id.btneliminar)

        eliminar.setOnClickListener {
            eliminarPais()
        }

        ///Provincias


        val cod_provincia = findViewById<EditText>(R.id.cod_provincia)
        val nombre_provincia = findViewById<EditText>(R.id.nombre_provincia)
        val capital_provincia = findViewById<EditText>(R.id.capital_provincia)
        val superfice = findViewById<EditText>(R.id.superficie_provincia)
        val cantidad_habitantes = findViewById<EditText>(R.id.cantidad_habitantes_provincia)
        val fecha_festividad_capital = findViewById<EditText>(R.id.fecha_fiesta_capital)


        val eliminar_p = findViewById<Button>(R.id.btn_eliminar_p)

        eliminar_p.setOnClickListener {
            eliminarProvincia(nombre_provincia, nombre_pais)
        }


        val ingresarProvincia = findViewById<Button>(R.id.ingresarProvincia)

        ingresarProvincia.setOnClickListener {
            ingresarProvincia(
                cod_provincia,
                nombre_provincia,
                capital_provincia,
                superfice,
                cantidad_habitantes,
                fecha_festividad_capital
            )
        }
        val buscarProvincia = findViewById<Button>(R.id.btn_buscar_p)

        buscarProvincia.setOnClickListener {
            buscarProvincia(
                cod_provincia,
                nombre_provincia,
                capital_provincia,
                superfice,
                cantidad_habitantes,
                fecha_festividad_capital
            )
        }

        val actualizarProvincia = findViewById<Button>(R.id.btn_actualizar_p)

        actualizarProvincia.setOnClickListener {
            actualizarProvincia(
                cod_provincia,
                nombre_provincia,
                capital_provincia,
                superfice,
                cantidad_habitantes,
                fecha_festividad_capital
            )
        }

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


    //Paises
    private fun ingresarPais(
        cod_pais: EditText,
        tasa_mort: EditText,
        nombre_pais: EditText,
        sw1: Switch,
        cantidad_habitantes_pais: EditText,
        capital_pais: EditText
    ) {

        var switchToF = false
        if (sw1.isChecked()) {
            switchToF = true
        }

        paises.add(
            Pais(
                Integer.parseInt(cod_pais.text.toString()),
                nombre_pais.text.toString(),
                capital_pais.text.toString(),
                Integer.parseInt(
                    cantidad_habitantes_pais.text.toString()
                ),
                tasa_mort.text.toString().toDouble(),
                switchToF,
                provincias
            )
        )
        guardarJson(paises)
        showToast("Pais ingresado correctamente")
        limpiarEntradaTexto()

    }

    private fun buscarPais(
        cod_pais: EditText,
        tasa_mort: EditText,
        nombre_pais: EditText,
        sw1: Switch,
        cantidad_habitantes_pais: EditText,
        capital_pais: EditText
    ) {

        leerJson()
        paises.forEach {

            if (it.nombre_pais == nombre_pais.text.toString()) {

                cod_pais.setText(it.cod_pais.toString())
                capital_pais.setText(it.capital_pais)
                cantidad_habitantes_pais.setText(it.cantidad_habitantes_pais.toString())
                tasa_mort.setText(it.tasa_mortalidad.toString())

                showToast("Se ha encontrado el pais")

            }

        }


    }

    private fun actualizarPais(
        cod_pais: EditText,
        tasa_mort: EditText,
        nombre_pais: EditText,
        sw1: Switch,
        cantidad_habitantes_pais: EditText,
        capital_pais: EditText
    ) {
        leerJson()
        var switchToF = false
        if (sw1.isChecked()) {
            switchToF = true
        }
        paises.forEachIndexed { index, element ->
            if (element.nombre_pais == nombre_pais.text.toString()) {
                provincias.clear()
                provincias.addAll(element.provincicas)
                paises[index] = Pais(
                    Integer.parseInt(cod_pais.text.toString()),
                    nombre_pais.text.toString(),
                    capital_pais.text.toString(),
                    Integer.parseInt(cantidad_habitantes_pais.text.toString()),
                    tasa_mort.text.toString().toDouble(),
                    switchToF,
                    provincias
                )
            }

        }
        guardarJson(paises)
        showToast("Se ha actualizado correctamente el pais")
        limpiarEntradaTexto()
    }

    private fun eliminarPais() {

        leerJson()
        var arrayone = arrayListOf<Pais>()
        val nombre_pais = findViewById<EditText>(R.id.nombre_pais)

        arrayone = paises.filterIndexed { index2, elemento2 ->
            elemento2.nombre_pais != nombre_pais.text.toString() // you can also specify more interesting filters here...
        } as ArrayList<Pais>
        guardarJson(arrayone)
        showToast("Se ha eliminado correctamente el pais")
        limpiarEntradaTexto()
    }

    private fun limpiarEntradaTexto() {


        val cod_pais = findViewById<EditText>(R.id.cod_pais)
        val nombre_pais = findViewById<EditText>(R.id.nombre_pais)
        val capital_pais = findViewById<EditText>(R.id.capital_pais)
        val tasa_mort = findViewById<EditText>(R.id.tasa_mortalidad)
        val cantidad_hab = findViewById<EditText>(R.id.cantidad_habitantes_pais)

        cod_pais.setText("")
        nombre_pais.setText("")
        capital_pais.setText("")
        cantidad_hab.setText("")
        tasa_mort.setText("")

    }

    //Provincias

    private fun ingresarProvincia(
        cod_provincia: EditText,
        nombre_provincia: EditText,
        capital_provincia: EditText,
        superfice: EditText,
        cantidad_habitantes: EditText,
        fecha_festividad_capital: EditText

    ) {

        leerJson()

        paises.forEachIndexed { index, element ->
            if (element.nombre_pais == nombre_pais.text.toString()) {
                provincias.clear()
                provincias.addAll(element.provincicas)
                provincias.add(
                    Provincia(
                        Integer.parseInt(cod_provincia.text.toString()),
                        nombre_provincia.text.toString(),
                        capital_provincia.text.toString(),
                        fecha_festividad_capital.text.toString(),
                        superfice.text.toString().toDouble(),
                        Integer.parseInt(cantidad_habitantes.text.toString())
                    )
                )
                paises[index] = Pais(
                    element.cod_pais,
                    element.nombre_pais,
                    element.capital_pais,
                    element.cantidad_habitantes_pais,
                    element.tasa_mortalidad,
                    element.pais_capitalista,
                    provincias
                )
            }

        }

        guardarJson(paises)
        showToast("Se ha ingresado la provincia ${nombre_provincia.text.toString()} al pais de ${nombre_pais.text.toString()}")
        limpiarEntradaTextoP()

    }

    private fun eliminarProvincia(nombre_provincia: EditText, nombre_pais: EditText) {

        leerJson()
        var arraytwo = arrayListOf<Provincia>()

        paises.forEachIndexed { index, element ->
            if (element.nombre_pais == nombre_pais.text.toString()) {
                provincias.clear()
                provincias.addAll(element.provincicas)
                arraytwo = provincias.filterIndexed { index2, elemento2 ->
                    elemento2.nombre_provincia != nombre_provincia.text.toString() // you can also specify more interesting filters here...
                } as ArrayList<Provincia>
                paises[index] = Pais(
                    element.cod_pais,
                    element.nombre_pais,
                    element.capital_pais,
                    element.cantidad_habitantes_pais,
                    element.tasa_mortalidad,
                    element.pais_capitalista,
                    arraytwo
                )
            }
        }

        guardarJson(paises)
        showToast("Se ha eliminado correctamente la provincia")

        limpiarEntradaTextoP()

    }


    private fun buscarProvincia(
        cod_provincia: EditText,
        nombre_provincia: EditText,
        capital_provincia: EditText,
        superfice: EditText,
        cantidad_habitantes: EditText,
        fecha_festividad_capital: EditText
    ) {

        leerJson()


        paises.forEach {

            if (it.nombre_pais == nombre_pais.text.toString()) {
                val provincias = arrayListOf<Provincia>()
                provincias.addAll(it.provincicas)

                provincias.forEach {
                    if (it.nombre_provincia == nombre_provincia.text.toString()) {

                        showToast("Se ha encontrado la provincia de ${nombre_pais.text.toString()}")
                        cod_provincia.setText(it.codigo_provincia.toString())
                        nombre_provincia.setText(it.nombre_provincia)
                        capital_provincia.setText(it.capital_provincia)
                        superfice.setText(it.superficie_provincia.toString())
                        cantidad_habitantes.setText(it.cantidad_habitantes_provincia.toString())
                        fecha_festividad_capital.setText(it.fecha_fiesta_capital)
                    }
                }

            }

        }

    }

    private fun actualizarProvincia(
        cod_provincia: EditText,
        nombre_provincia: EditText,
        capital_provincia: EditText,
        superfice: EditText,
        cantidad_habitantes: EditText,
        fecha_festividad_capital: EditText
    ) {

        leerJson()

        paises.forEachIndexed { index, element ->
            if (element.nombre_pais == nombre_pais.text.toString()) {
                provincias.clear()
                provincias.addAll(element.provincicas)
                provincias.forEachIndexed { index1, element1 ->

                    if (element1.nombre_provincia == nombre_provincia.text.toString()) {
                        provincias[index1] = Provincia(
                            Integer.parseInt(cod_provincia.text.toString()),
                            nombre_provincia.text.toString(),
                            capital_provincia.text.toString(),
                            fecha_festividad_capital.text.toString(),
                            superfice.text.toString().toDouble(),
                            Integer.parseInt(cantidad_habitantes.text.toString())
                        )
                    }
                }
                paises[index] = Pais(
                    element.cod_pais,
                    element.nombre_pais,
                    element.capital_pais,
                    element.cantidad_habitantes_pais,
                    element.tasa_mortalidad,
                    element.pais_capitalista,
                    provincias
                )
            }

        }

        guardarJson(paises)
        showToast("Se ha movidificado correctamente la provincia")

        limpiarEntradaTextoP()

    }

    private fun limpiarEntradaTextoP() {


        val cod_provincia = findViewById<EditText>(R.id.cod_provincia)
        val nombre_provincia = findViewById<EditText>(R.id.nombre_provincia)
        val capital_provincia = findViewById<EditText>(R.id.capital_provincia)
        val superficie_provincia = findViewById<EditText>(R.id.superficie_provincia)
        val cantidad_hab = findViewById<EditText>(R.id.cantidad_habitantes_provincia)
        val fecha = findViewById<EditText>(R.id.fecha_fiesta_capital)

        cod_provincia.setText("")
        nombre_provincia.setText("")
        capital_provincia.setText("")
        superficie_provincia.setText("")
        cantidad_hab.setText("")
        fecha.setText(" ")

    }

    //leer Json
    private fun leerJson() {
        val archivo = InputStreamReader(openFileInput("myJson.json"))
        val br = BufferedReader(archivo)
        var linea = br.readLine()
        var administracion_pais = gson.fromJson(linea, Administracion::class.java)

        paises = administracion_pais.paises
    }
    //Gestion del archivo Json

    private fun guardarJson(paises: ArrayList<Pais>) {

        val administracion = Administracion(paises)
        val datajson = gson.toJson(administracion)

        try {
            val archivo = OutputStreamWriter(openFileOutput("myJson.json", Context.MODE_PRIVATE))
            archivo.write(datajson)
            archivo.flush()
            archivo.close()
        } catch (e: IOException) {
        }

    }


    fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }


}