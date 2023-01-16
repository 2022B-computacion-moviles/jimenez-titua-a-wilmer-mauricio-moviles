package com.example.movcomwmjt

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador (
    contexto: Context?,
        ): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1


        ) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean{
        //this.readableDatabase Lectura
        //this.writableDatabase Escritura
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion",descripcion)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", //tabla
            null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt()==-1) false else true

    }

    fun eliminarEntrenadorFormulario(id:Int): Boolean{
        //val conexionEscritura = this.writableDatabase
        val conexionEscritura = writableDatabase

        // "SELECT * FROM ENTRENADOR WHERE ID = ?"
        // arrayOf(
        //  id.toString()
        //)

        val resultadoEliminación = conexionEscritura
            .delete(
                "ENTRENADOR", // Tabla
            "id=?",
                arrayOf(
                    id.toString()
                )

            )
        conexionEscritura.close()
        return if(resultadoEliminación.toInt()==-1) false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        idActualizar: Int
    ):Boolean{
        val conexionEscritura =writableDatabase
        val valoresAActualizar= ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        val resultadoActualizacion= conexionEscritura
            .update(
                "ENTRENADOR",// Nombre tabla
                valoresAActualizar, // valores a actualizar
                "id=?",// clausula where
                arrayOf(
                    idActualizar.toString()
                )// Parametros clusula were
            )
        conexionEscritura.close()
        return if(resultadoActualizacion==-1) false else true
    }

    fun consultarEntrenadorPorId(id: Int): BEntrenador{
        // val baseDatosLectura0 this.readableDatabase
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario= " SELECT * FROM ENTRENADOR WHERE" +
                " ID=? "
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            arrayOf(
                id.toString()
            )

        )
        val existeUsuario=resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado= BEntrenador(0, "","")
        //LOGICA OBTENER EL USUARIO

        do {
            val id= resultadoConsultaLectura.getInt(0) // columna indice 0 - ° ID
            val nombre=resultadoConsultaLectura.getString(1)// columna indice 1 - ° NOMBRE
            val descripcion=
                resultadoConsultaLectura.getString(2)// Columna indice 2 -> DESCRIPCION
            if(id != null){
                usuarioEncontrado.id=id
                usuarioEncontrado.nombre= nombre
                usuarioEncontrado.descripcion=descripcion

            }
        }while (resultadoConsultaLectura.moveToNext())
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado

    }
}