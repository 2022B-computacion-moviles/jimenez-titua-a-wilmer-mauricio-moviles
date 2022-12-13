import java.util.Date

fun main(args: Array<String>) {
    println("Hola Mundo!")
    //T°ipo de variables,l
    //Inmutables (no re asignar) =
    val inmutable: String = "Vicente";
    //inmutable = "Adrian"

    //Mutables (re asignar) =
    var mutable:String = "Vicente";
    mutable = "Adrian";

    // val > vars

    // Duck Typing

    val ejemploVaribale = "Ejemplo"
    ejemploVaribale.trim()
    val edadEjemplo: Int = 12

    // Variables pirmitivas
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double =1.2
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true

    // Clases
    val fechaNacimiento: Date = Date() // no se usa "new" en clases
    if(true){

    }else{

    }

    // Switch no exite
    val estadoCivWhen ="S"
    when (estadoCivWhen){
        ("S")->{
            println("Soltero")
        }
        "C" -> println("Casado")
        else -> println("Desconocido")
    }

    val coqueto = if(estadoCivWhen == "S") println("Si") else println("No")
}

fun imprimirNombre(nombre:String) : Unit{
    println("Nombre : ${nombre}")
}
fun calcularSueldo(
    sueldo: Double, // requerido
    tasa: Double = 12.00, // opcional por defecto
    bonoEspecial: Double?  = null // opcional nulo
):Double{
    //String -> String?
    //Int -> Int
    //Date -> Date?
    if(bonoEspecial != null){
        return sueldo * tasa * bonoEspecial
    }
    return sueldo * tasa
}

//Clases

abstract  class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int, // parametro
        dos: Int // parametro
    ){
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Iniciando")
    }
}



abstract class Numeros( //Constructor Primario
//uno: Int, // Parametro
// public var uno: Int, //Propiedad de la clase publica
   protected val numeroUno: Int, //propiedad
   protected val numeroDos: Int, //propiedad
){
    init { //Bloque codigo constructor primario
        //this.numeroDos // "this" opcional
        //this.numeroUno
        numeroUno
        numeroDos
        println("Iniciando")

    }
}

class Suma( //Constructor Primario Suma
uno: Int,
dos: Int// Parametro
) : Numeros ( // Heredamos de la clase Numeros
    //super constrcutro nuemros
    uno,
    dos,
) {
    init { // Bloque constructor primario
        this.numeroUno
        this.numeroDos
    }

    constructor( //Segundo constructor
        uno: Int?,
        dos: Int //Parametros
    ) : this(
        if (uno == null) 0 else uno,
        dos
    ) {}
    constructor( //Tercer constructor
        uno: Int?,
        dos: Int //Parametros
    ) : this(
        uno,

        if (dos == null) 0 else dos,

    ) {}
    constructor( //Segundo constructor
        uno: Int?,
        dos: Int //Parametros
    ) : this(
        if (uno == null) 0 else unos,
        dos
    ) {
    }
}