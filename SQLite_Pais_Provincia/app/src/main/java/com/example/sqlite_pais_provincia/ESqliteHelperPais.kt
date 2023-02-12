package com.example.sqlite_pais_provincia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperPais(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "paises",
    null,
    1
)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaPais =
            """
               CREATE TABLE PAIS(
               id_pais INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre_pais VARCHAR(50),
               capital_pais VARCHAR(50),
               cantidad_habitantes_pais INTEGER,
               tasa_mortalidad DECIMAL,
               pais_capitalista INTEGER
               ) 
            """.trimIndent()


        val scriptSQLCrearTablaProvincia =
            """
               CREATE TABLE PROVINCIA(
               id_provincia INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre_provincia VARCHAR(50),
               capital_provincia VARCHAR(50),
               cantidad_habitantes_provincia INTEGER,
               superfice_provincia DECIMAL,
               fecha_fiesta_capital VARCHAR(20),
               id_pais INTEGER
               ) 
            """.trimIndent()



        db?.execSQL(scriptSQLCrearTablaPais)
        db?.execSQL(scriptSQLCrearTablaProvincia)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearPais(
        nombre_pais: String,
        capital_pais: String,
        cantidad_habitantes_pais: Int,
        tasa_mortalidad: Double,
        pais_capitalista: Int

    ):Boolean{

        val basedatosEscritura = writableDatabase
        val valoresGuardar=  ContentValues()
        valoresGuardar.put("nombre_pais",nombre_pais)
        valoresGuardar.put("capital_pais",capital_pais)
        valoresGuardar.put("cantidad_habitantes_pais",cantidad_habitantes_pais)
        valoresGuardar.put("tasa_mortalidad",tasa_mortalidad)
        valoresGuardar.put("pais_capitalista",pais_capitalista)


        val resultadoGuardar= basedatosEscritura
            .insert(
                "PAIS",
                null,
                valoresGuardar
            )

        basedatosEscritura.close()
        return if(resultadoGuardar.toInt()==-1) false else true


    }

    fun mostrarPaises(): ArrayList<Pais> {

        var paises = ArrayList<Pais>()
        val baseDatosLectura = readableDatabase
        var paisesEncontrados= Pais(0,"","",0,0.0,0)
        val scriptConsultarPaises = "SELECT * FROM PAIS"
        val resultadoConsultaLectura=baseDatosLectura.rawQuery(
            scriptConsultarPaises,
            null
        )
        if(resultadoConsultaLectura!=null && resultadoConsultaLectura.count!=0){
            resultadoConsultaLectura.moveToFirst()
            do{
                val codigo_pais=resultadoConsultaLectura.getInt(0)
                val nombre_pais=resultadoConsultaLectura.getString(1)
                val capital_pais=resultadoConsultaLectura.getString(2)
                val cantidad_habitantes_pais=resultadoConsultaLectura.getInt(3)
                val tasa_mortalidad=resultadoConsultaLectura.getDouble(4)
                val pais_capitalista=resultadoConsultaLectura.getInt(5)

                if(codigo_pais!=null){
                    paisesEncontrados.cod_pais=codigo_pais
                    paisesEncontrados.nombre_pais= nombre_pais
                    paisesEncontrados.capital_pais= capital_pais
                    paisesEncontrados.cantidad_habitantes_pais=cantidad_habitantes_pais
                    paisesEncontrados.tasa_mortalidad=tasa_mortalidad
                    paisesEncontrados.pais_capitalista=pais_capitalista


                }
                paises.add(paisesEncontrados)
                paisesEncontrados=Pais(0,"","",0,0.0,0)


            }while (resultadoConsultaLectura.moveToNext())
        }else{
            paises=ArrayList<Pais>()
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return paises

    }

    fun eliminarPais(codigo_pais: Int):Boolean{

        val basedatosEscritura=writableDatabase
        val resultadoEliminación=basedatosEscritura
            .delete(
                "PAIS",
                "id_pais=?",
                arrayOf(
                    codigo_pais.toString()
                )
            )

        basedatosEscritura.close()
        return if(resultadoEliminación.toInt()==-1)false else true


    }

    fun editarPais(
        codigo_pais: Int,
        nombre_pais: String,
        capital_pais: String,
        cantidad_habitantes_pais: Int,
        tasa_mortalidad: Double,
        pais_capitalista: Int):Boolean{


        val basedatosEscritura = writableDatabase
        val valoresAActualizar=  ContentValues()
        valoresAActualizar.put("nombre_pais",nombre_pais)
        valoresAActualizar.put("capital_pais",capital_pais)
        valoresAActualizar.put("cantidad_habitantes_pais",cantidad_habitantes_pais)
        valoresAActualizar.put("tasa_mortalidad",tasa_mortalidad)
        valoresAActualizar.put("pais_capitalista",pais_capitalista)

        val resultadoActualizacion=basedatosEscritura
            .update(
                "PAIS",
                valoresAActualizar,
                "id_pais=?",
                arrayOf(
                    codigo_pais.toString()
                )
            )

        basedatosEscritura.close()
        return if (resultadoActualizacion==-1)false else true



    }


    fun crearProvincia(
        nombre_provincia: String,
        capital_provincia: String,
        fecha_fiesta_capital: String,
        superficie_provincia: Double,
        cantidad_habitantes_provincia: Int,
        codigo_pais: Int

    ):Boolean{

        val basedatosEscritura = writableDatabase
        val valoresGuardarP=  ContentValues()

        valoresGuardarP.put("nombre_provincia",nombre_provincia)
        valoresGuardarP.put("capital_provincia",capital_provincia)
        valoresGuardarP.put("cantidad_habitantes_provincia",cantidad_habitantes_provincia)
        valoresGuardarP.put("superfice_provincia",superficie_provincia)
        valoresGuardarP.put("fecha_fiesta_capital",fecha_fiesta_capital)
        valoresGuardarP.put("id_pais",codigo_pais)


        val resultadoGuardarP= basedatosEscritura
            .insert(
                "PROVINCIA",
                null,
                valoresGuardarP
            )

        basedatosEscritura.close()

        return if(resultadoGuardarP.toInt()==-1) false else true


    }



   fun mostrarProvincias(codigo_pais: Int): ArrayList<Provincia> {

       var provincias = ArrayList<Provincia>()
        val baseDatosLectura = readableDatabase
        var provinciasEncontrados = Provincia(0, "", "", "", 0.0, 0,0)
        val scriptConsultarProvincias = "SELECT * FROM PROVINCIA WHERE id_pais=${codigo_pais}"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarProvincias,
            null
        )


        if(resultadoConsultaLectura!=null && resultadoConsultaLectura.count!=0){
            resultadoConsultaLectura.moveToFirst()
            do {


                val codigo_provincia = resultadoConsultaLectura.getInt(0)
                val nombre_provincia = resultadoConsultaLectura.getString(1)
                val capital_provincia = resultadoConsultaLectura.getString(2)
                val cantidad_habitantes_provincia = resultadoConsultaLectura.getInt(3)
                val superficie_provincia = resultadoConsultaLectura.getDouble(4)
                val fecha_fiesta_capital = resultadoConsultaLectura.getString(5)




                if(codigo_pais!=null){
                    provinciasEncontrados.codigo_provincia=codigo_provincia
                    provinciasEncontrados.nombre_provincia=nombre_provincia
                    provinciasEncontrados.capital_provincia=capital_provincia
                    provinciasEncontrados.cantidad_habitantes_provincia=cantidad_habitantes_provincia
                    provinciasEncontrados.superficie_provincia=superficie_provincia
                    provinciasEncontrados.fecha_fiesta_capital=fecha_fiesta_capital



                }
                provincias.add(provinciasEncontrados)
                provinciasEncontrados = Provincia(0, "", "", "", 0.0, 0,0)


            }while (resultadoConsultaLectura.moveToNext())
        }else{


            provincias=ArrayList<Provincia>()
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return provincias


    }

    fun eliminarProvincia(codigo_provincia: Int):Boolean{

        val basedatosEscritura=writableDatabase
        val resultadoEliminación=basedatosEscritura
            .delete(
                "PROVINCIA",
                "id_provincia=?",
                arrayOf(
                    codigo_provincia.toString()
                )
            )

        basedatosEscritura.close()
        return if(resultadoEliminación.toInt()==-1)false else true

    }

    fun editarProvincia(
        codigo_provincia: Int,
        nombre_provincia: String,
        capital_provincia: String,
        fecha_fiesta_capital: String,
        superficie_provincia: Double,
        cantidad_habitantes_provincia: Int

    ):Boolean{

        val basedatosEscritura = writableDatabase
        val valoresAActualizarPro=  ContentValues()
        valoresAActualizarPro.put("nombre_provincia",nombre_provincia)
        valoresAActualizarPro.put("capital_provincia",capital_provincia)
        valoresAActualizarPro.put("cantidad_habitantes_provincia",cantidad_habitantes_provincia)
        valoresAActualizarPro.put("superfice_provincia",superficie_provincia)
        valoresAActualizarPro.put("fecha_fiesta_capital",fecha_fiesta_capital)

        val resultadoActualizacion=basedatosEscritura
            .update(
                "PROVINCIA",
                valoresAActualizarPro,
                "id_provincia=?",
                arrayOf(
                    codigo_provincia.toString()
                )
            )

        basedatosEscritura.close()
        return if (resultadoActualizacion==-1)false else true


        return false

    }




}



