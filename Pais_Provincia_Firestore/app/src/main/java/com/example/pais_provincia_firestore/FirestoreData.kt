package com.example.pais_provincia_firestore

import android.content.ContentValues
import android.nfc.Tag
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class FirestoreData {

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var arreglo_Pais = ArrayList<Pais>()


    fun crearPais(
        nombre_pais: String,
        capital_pais: String,
        cantidad_habitantes_pais: Int,
        tasa_mortalidad: Double,
        pais_capitalista: Int

    ) {

        val dato = hashMapOf(
            "nombre_pais" to nombre_pais,
            "capital_pais" to capital_pais,
            "cantidad_habitantes_pais" to cantidad_habitantes_pais,
            "tasa_mortalidad" to tasa_mortalidad,
            "pais_capitalista" to pais_capitalista
        )

        db.collection("pais")
            .add(dato)
            .addOnSuccessListener {
                Log.d("Ingreso de Pais", "Correcta")
            }
            .addOnFailureListener {
                Log.d("Ingreso de Pais", "Incorrecta")
            }

    }


    fun editarPais(
        codigo_pais: String,
        nombre_pais: String,
        capital_pais: String,
        cantidad_habitantes_pais: Int,
        tasa_mortalidad: Double,
        pais_capitalista: Int)
    {
        val dato = hashMapOf(
            "nombre_pais" to nombre_pais,
            "capital_pais" to capital_pais,
            "cantidad_habitantes_pais" to cantidad_habitantes_pais,
            "tasa_mortalidad" to tasa_mortalidad,
            "pais_capitalista" to pais_capitalista
        )

        db.collection("pais").document(codigo_pais)
            .set(dato)
            .addOnSuccessListener {
                Log.d("Actualización de Pais", "Correcta")
            }
            .addOnFailureListener {
                Log.d("Actualización de Pais", "Incorrecta")
            }


    }

    fun eliminarPais(codigo_pais: String){
        db.collection("pais").document(codigo_pais)
            .delete()
            .addOnSuccessListener {
                Log.d("Eliminación de Pais", "Correcta")
            }
            .addOnFailureListener {
                Log.d("Eliminación de Pais", "Incorrecta")
            }


    }

    fun crearProvincia(
        nombre_provincia: String,
        capital_provincia: String,
        fecha_fiesta_capital: String,
        superficie_provincia: Double,
        cantidad_habitantes_provincia: Int,
        codigo_pais: String

    ){


        val dato = hashMapOf(
            "nombre_provincia" to nombre_provincia,
            "capital_provincia" to capital_provincia,
            "fecha_fiesta_capital" to fecha_fiesta_capital,
            "superficie_provincia" to superficie_provincia,
            "cantidad_habitantes_provincia" to cantidad_habitantes_provincia,
        )

        db.collection("pais").document(codigo_pais)
            .collection("provincias")
            .add(dato)
            .addOnSuccessListener {
                Log.d("Ingreso de Provincia", "Correcta")
            }
            .addOnFailureListener {
                Log.d("Ingreso de Provincia", "Incorrecta")
            }


    }


    fun eliminarProvincia(codigo_provincia: String, codigo_pais: String){

        db.collection("pais").document(codigo_pais)
            .collection("provincias")
            .document(codigo_provincia)
            .delete()
            .addOnSuccessListener {
                Log.d("Eliminación de Provincia", "Correcta")


            }
            .addOnFailureListener{
                Log.d("Eliminación de Provincia", "Incorrecto")


            }





    }

    fun editarProvincia(
        codigo_provincia: String,
        nombre_provincia: String,
        capital_provincia: String,
        fecha_fiesta_capital: String,
        superficie_provincia: Double,
        cantidad_habitantes_provincia: Int,
        codigo_pais: String


    ){

        val dato = hashMapOf(
            "nombre_provincia" to nombre_provincia,
            "capital_provincia" to capital_provincia,
            "fecha_fiesta_capital" to fecha_fiesta_capital,
            "superficie_provincia" to superficie_provincia,
            "cantidad_habitantes_provincia" to cantidad_habitantes_provincia,
        )

        db.collection("pais").document(codigo_pais)
            .collection("provincias")
            .document(codigo_provincia)
            .set(dato)
            .addOnSuccessListener {
                Log.d("Actualización de Provincia", "Correcta")
            }
            .addOnFailureListener {
                Log.d("Actualización de Provincia", "Incorrecta")
            }


    }




}