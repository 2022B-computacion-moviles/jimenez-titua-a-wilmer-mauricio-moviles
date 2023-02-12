package com.example.sqlite_pais_provincia

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    var arreglosPaises= ArrayList<Pais>()
    var idItemSeleccionado =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializamos la base de datos

        EBaseDeDatos.TablaPais = ESqliteHelperPais(this)

        val boton_crear_pais=findViewById<Button>(R.id.btn_crear_pais)
        boton_crear_pais.setOnClickListener {
            irActividad(Crear_Pais::class.java)

        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        //LLenamos las opciones del menu
        val inflater=menuInflater
        inflater.inflate(R.menu.menu_pais, menu)
        //Obtener el id del ArrayListSeleccionada
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado=id

    }


    var paisSeleccionado= Pais(0,"","",0,0.0,0)

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar_pais->{


                val listView_pais=findViewById<ListView>(R.id.lv_lista_paises)
                var itemselect= listView_pais.getItemAtPosition(idItemSeleccionado)
                var objetoSeleccionado= itemselect as Pais

                paisSeleccionado=objetoSeleccionado

                codigo_pais=paisSeleccionado.cod_pais.toString()
                nombre_pais= paisSeleccionado.nombre_pais.toString()
                capital_pais= paisSeleccionado.capital_pais.toString()
                cantidad_habitantes_pais= paisSeleccionado.cantidad_habitantes_pais.toString()
                tasa_mort= paisSeleccionado.tasa_mortalidad.toString()
                capitalista_pais= paisSeleccionado.pais_capitalista.toString()





                parametrosPaisSeleccionado(Editar_Pais::class.java)


                return true
            }
            R.id.mi_eliminar_pais->{
                val listView_pais=findViewById<ListView>(R.id.lv_lista_paises)
                var itemselect= listView_pais.getItemAtPosition(idItemSeleccionado)
                var objetoSeleccionado= itemselect as Pais

                paisSeleccionado=objetoSeleccionado
                EBaseDeDatos.TablaPais!!.eliminarPais(objetoSeleccionado.cod_pais)
                onResume()
                return true
            }
            R.id.mi_ver_provincias->{

                val listView_pais=findViewById<ListView>(R.id.lv_lista_paises)
                var itemselect= listView_pais.getItemAtPosition(idItemSeleccionado)
                var objetoSeleccionado= itemselect as Pais
                paisSeleccionado=objetoSeleccionado
                codigo_pais=paisSeleccionado.cod_pais.toString()
                nombre_pais= paisSeleccionado.nombre_pais.toString()
                parametroID(Mostrar_Provincias::class.java)
                return true
            }
            else->super.onContextItemSelected(item)

        }
    }

    override fun onResume() {
        super.onResume()

        arreglosPaises = EBaseDeDatos.TablaPais!!.mostrarPaises()

        val listView=findViewById<ListView>(R.id.lv_lista_paises)

        val adaptador=Adaptador_Pais(this,arreglosPaises)
        listView.adapter=adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)


    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }

    var codigo_pais=""
    var nombre_pais =""
    var capital_pais = ""
    var cantidad_habitantes_pais = ""
    var tasa_mort = ""
    var capitalista_pais=""

    fun parametrosPaisSeleccionado(
        clase: Class<*>
    ){
        val intentPaisSeleccionado=Intent(this, clase)
        intentPaisSeleccionado.putExtra("codigo_pais", codigo_pais )
        intentPaisSeleccionado.putExtra("nombre_pais", nombre_pais )
        intentPaisSeleccionado.putExtra("capital_pais", capital_pais)
        intentPaisSeleccionado.putExtra("cantidad_habitantes_pais", cantidad_habitantes_pais )
        intentPaisSeleccionado.putExtra("tasa_mort", tasa_mort)
        intentPaisSeleccionado.putExtra("capitalista_pais",capitalista_pais)

        startActivity(intentPaisSeleccionado)
    }

    fun parametroID(
        clase: Class<*>
    ) {
        val intenProvinciasDePais=Intent(this,clase)
        intenProvinciasDePais.putExtra("codigo_pais",codigo_pais)
        intenProvinciasDePais.putExtra("nombre_pais",nombre_pais)
        startActivity(intenProvinciasDePais)
    }


    fun Context.showToast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()



}