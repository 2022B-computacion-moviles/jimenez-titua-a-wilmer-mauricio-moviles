import java.util.*

import kotlin.collections.ArrayList


fun main(args: Array<String>) {

    println("Hello World!")

    //Tipos de variables

    //Variables inmutables (no resignar) =

    val inmutable: String = "Sergio";

    //inmutable = "Juan"


    //Variables mutables (re asignar)

    var mutable: String = "Sergio";

    mutable = "Juan";


    //val > vars


    //Duck Typing

    var ejemploVariable = "Ejemplo"

    ejemploVariable.trim()

    val edadEjemplo = 12  //declarar varibles sin necesidad de escribir el tipo


    //Variable primitivas

    val nombreProfesor: String = "Adrian Eguez"

    val sueldo: Double = 1.2

    val estadoCivil: Char = 'S'

    val mayorEdad = true


    //Clases

    val fechaNacimiento: Date = Date()    //no se usa "new" en clases


    //if

    if (true) {


    } else {


    }


    //swtch no existe

    val estadoCivilWhen = 'S'

    when (estadoCivilWhen) {

        ('S') -> {

            println("Soltero")

        }

        'C' -> println("Casado")

        else -> println("Desconocido")

    }

    val coqueto = if (estadoCivilWhen == 'S') println("Si") else println("No")

    val sumaUno = Suma(1, 2)


    val sumaDos = Suma(1, null)

    val sumaTres = Suma(null, 2)

    val sumaCuatro = Suma(null, null)



    sumaUno.sumar()

    sumaDos.sumar()

    sumaTres.sumar()

    sumaCuatro.sumar()



    println(Suma.historiaSumas)


    //ARREGLOS

    //Tipos de Arreglo


    //Arreglo estatico

    val arregloEstatico: ArrayList<Int> = arrayListOf(1, 2, 3)

    println(arregloEstatico)


    //Arreglo Dinamico

    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    println(arregloDinamico)

    arregloDinamico.add(11)

    arregloDinamico.add(12)

    println(arregloDinamico)


    //OPERADORES -> Sirven para los arregloa estaticos y dinamicos

    //FOR EACH -> Unit

    //Iterar un arreglo

    println("ForEach")

    val respuestaForEach: Unit = arregloDinamico

        .forEach {

                valorActual: Int ->

            println("Valor actual: ${valorActual}")

        }

    arregloEstatico

        .forEachIndexed { indice: Int, valorActual: Int ->

            println("Valor ${valorActual} Indice: ${indice}")

        }

    println(respuestaForEach)


    //MAP -> Muta el arreglo (cambia el arreglo)
    //1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve el valor de cada iteracion

    println("MAP")

    val respuestaMap: List<Double> = arregloDinamico

        .map { valorActual: Int ->

            return@map valorActual.toDouble() + 100.00

        }

    println(respuestaMap)


    val respuestaMapDos = arregloDinamico.map { it + 15 }

    //  .map {valorActual: Int ->

    //            return@map valorActual.toDouble() + 100.00

    //   }

    println(respuestaMapDos)


    //Filter -> Filtrar el arreglo

    //1)Devolver una expresion (True o False)

    //2)Nuevo arreglo filtrado

    println("FILTER")

    val respuestasFilter: List<Int> = arregloDinamico

        .filter { valorActual: Int ->

            val mayoresaCinco: Boolean = valorActual > 5 // Expresion de condicion

            return@filter mayoresaCinco

        }


    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }

    println(respuestasFilter)

    println(respuestaFilterDos)

    //OR AND
    //OR - > ANY( Aluno cumple?)
    //AND -> ALl ( Todos cumplen?)

    val respuestaAny: Boolean= arregloDinamico
        .any{ valorActual: Int->
            return@any (valorActual>5)
        }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //false

    //Reduce -> valor acumulado
    //Valor acumulado = 0 (Siempre 0 en lenguaje kotlin)
    //[1,2,3,4,5] - > Sumeme todos los valores del arreglo
    //valorIteracion1 = valorEmpieza +1 =0+1=1 -> Iteracion -> 1
    //valorIteracion2 = valorIteracion1 +2 =1+2=3 -> Iteracion -> 2
    //valorIteracion3 = valorIteracion2 +3 =3+3=6 -> Iteracion -> 3
    //valorIteracion4 = valorIteracion3 +4 =6+4=10 -> Iteracion -> 4
    //valorIteracion5 = valorIteracion4 +2 =10+5=15-> Iteracion -> 5

    val respuestaReduce: Int = arregloDinamico
        .reduce { //acumulado =0 -> SIEMPRE EMPIEZA EN 0,
            acumulado: Int,  valorActual: Int->
            return@reduce (acumulado + valorActual)// -> Logica negocio
         }
    println(respuestaReduce)

}

//**************************************************************//
fun imprimirNombre(nombre: String): Unit {

    println("Nombre: ${nombre}")

}


fun calcularSueldo(

    sueldo: Double, //requerido

    tasa: Double = 12.00, //opcional por defecto

    bonoEspecial: Double? = null //opcional nulo  //? indica que la variable puede ser nulo en algun momento

): Double {

    if (bonoEspecial != null) {

        return sueldo * tasa * bonoEspecial

    }

    return sueldo * tasa

}
//Clases

abstract class NumerosJava {

    protected val numeroUno: Int

    private val numeroDos: Int

    constructor(

        uno: Int, //Paramwtro

        dos: Int  //Parametro

    ) {

        this.numeroUno = uno

        this.numeroDos = dos

        println("Iniciando")

    }

}


abstract class Numeros(//Constructor Primario

    //uno: Int, //Parametro

    //public var uno: Int //Propiedad

    protected val numeroUno: Int, //Propiedad

    protected val numeroDos: Int //Propiedad

) {

    init {//Bloque de codigo contructor primario

        //this.numeroUno = uno

        //this.numeroDos = dos

        numeroUno

        numeroDos

        println("Iniciando")

    }

}


class Suma(
//Costructor Primario Suma

    uno: Int, // Parametro

    dos: Int, // Parametro

) : Numeros(
    //Heredamos de la clase numero

    //super constructoe Numeros

    uno,

    dos,

    ) {

    init {//Bloque constructor primario

        this.numeroUno

        this.numeroDos

    }


    constructor(//segunndo constructor

        uno: Int?, //Parametro

        dos: Int   //Parametros

    ) : this(

        if (uno == null) 0 else uno,

        dos

    ) {
    }


    constructor(//tercer constructor

        uno: Int, //Parametro

        dos: Int?   //Parametros

    ) : this(

        uno,

        if (dos == null) 0 else dos,

        ) {
    }


    constructor(
//cuarto constructor

        uno: Int?, //Parametro

        dos: Int?,   //Parametros

    ) : this(

        if (uno == null) 0 else uno,

        if (dos == null) 0 else dos,

        ) {
    }


    fun sumar(): Int {

        val total = numeroUno + numeroDos

        agregarHistorial(total)

        return total

    }

    companion object {

        val pi = 3.14 // Suma.pi -> 3.14

        val historiaSumas = arrayListOf<Int>() //Suma.historialSumas

        fun agregarHistorial(valorNuevaSuma: Int) {

            historiaSumas.add(valorNuevaSuma)

        }

    }

}

