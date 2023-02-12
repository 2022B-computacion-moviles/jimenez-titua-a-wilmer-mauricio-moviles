package com.example.sqlite_pais_provincia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

class Mostrar_Provincias : AppCompatActivity() {

    var codigo_pais=""
    var nombre_pais=""
    var idItemSeleccionado =0
    var arreglosProvincias= ArrayList<Provincia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_provincias)

        val bundle=intent.extras
        codigo_pais= bundle?.getString("codigo_pais").toString()
        nombre_pais=bundle?.getString("nombre_pais").toString()


        val botoncrearProvincia= findViewById<Button>(R.id.btn_crear_provincia)
        botoncrearProvincia.setOnClickListener {
            parametroIDP(Crear_Provincia::class.java)
        }

    }

    override fun onResume() {
        super.onResume()

        val textTitulo=findViewById<TextView>(R.id.textView3)
        textTitulo.setText("Provincias de ${nombre_pais}")

        arreglosProvincias = EBaseDeDatos.TablaPais!!.mostrarProvincias(codigo_pais.toInt())

        val listView=findViewById<ListView>(R.id.lv_lista_provincias)

        val adaptador=Adaptador_Provincia(this,arreglosProvincias)
        listView.adapter=adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)


        val ir_paises=findViewById<Button>(R.id.btn_ir_paises)
        ir_paises.setOnClickListener {

            irActividad(MainActivity::class.java)

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
        inflater.inflate(R.menu.menu_provincia, menu)
        //Obtener el id del ArrayListSeleccionada
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado=id

    }

    var provinciaSeleccionado = Provincia(0, "", "", "", 0.0, 0,0)

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.mi_eliminar_provincia->{

                val listView_provincia=findViewById<ListView>(R.id.lv_lista_provincias)
                var itemselect= listView_provincia.getItemAtPosition(idItemSeleccionado)
                var objetoSeleccionado= itemselect as Provincia
                provinciaSeleccionado=objetoSeleccionado
                EBaseDeDatos.TablaPais!!.eliminarProvincia(objetoSeleccionado.codigo_provincia)
                onResume()


                return true
            }
            R.id.mi_editar_provincia->{

                val listView_provincia=findViewById<ListView>(R.id.lv_lista_provincias)
                var itemselect= listView_provincia.getItemAtPosition(idItemSeleccionado)
                var objetoSeleccionado= itemselect as Provincia

                provinciaSeleccionado=objetoSeleccionado

                codigo_provincia=provinciaSeleccionado.codigo_provincia.toString()
                nombre_provincia= provinciaSeleccionado.nombre_provincia
                capital_provincia= provinciaSeleccionado.capital_provincia
                cantidad_habitantes_provincia= provinciaSeleccionado.cantidad_habitantes_provincia.toString()
                superficie_provincia= provinciaSeleccionado.superficie_provincia.toString()
                fecha_fiesta_capital=provinciaSeleccionado.fecha_fiesta_capital
                id_pais=codigo_pais
                parametrosProvinciaSeleccionado(Editar_Provincia::class.java)


                return true
            }

            else->super.onContextItemSelected(item)

        }
    }



    fun parametroIDP(
        clase: Class<*>
    ) {
        val intenProvinciasDePaisID=Intent(this,clase)
        intenProvinciasDePaisID.putExtra("codigo_pais",codigo_pais)
        startActivity(intenProvinciasDePaisID)
    }


    var codigo_provincia=""
    var nombre_provincia =""
    var capital_provincia = ""
    var fecha_fiesta_capital = ""
    var superficie_provincia = ""
    var cantidad_habitantes_provincia=""
    var id_pais=""


    fun parametrosProvinciaSeleccionado(
        clase: Class<*>
    ){
        val intentPaisSeleccionado=Intent(this, clase)
        intentPaisSeleccionado.putExtra("codigo_provincia", codigo_provincia )
        intentPaisSeleccionado.putExtra("nombre_provincia", nombre_provincia )
        intentPaisSeleccionado.putExtra("capital_provincia", capital_provincia)
        intentPaisSeleccionado.putExtra("cantidad_habitantes_provincia", cantidad_habitantes_provincia )
        intentPaisSeleccionado.putExtra("superficie_provincia", superficie_provincia)
        intentPaisSeleccionado.putExtra("fecha_fiesta_capital",fecha_fiesta_capital)
        intentPaisSeleccionado.putExtra("id_pais",id_pais)
        intentPaisSeleccionado.putExtra("nombre_pais",nombre_pais)

        startActivity(intentPaisSeleccionado)
    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)

    }



}