package com.example.proyecto_iib

class Cita (
    var idPaciente:String,
    var idOdontologo:String,
    var idProcedimiento:String,
    var nombreProcedimiento: String,
    var hora:String,
    var fecha:String
        ){
    override fun toString(): String {
        return "Cita(idPaciente='$idPaciente', idOdontologo='$idOdontologo', idProcedimiento='$idProcedimiento', nombreProcedimiento='$nombreProcedimiento', hora='$hora', fecha='$fecha')"
    }
}