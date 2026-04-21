/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */

data class UserAccount(var uuid: String, var email: String, var balance: Double)

class Book {
    var title: String = ""
    var author: String = ""
    var pages: Int = 0
}

fun main() {
    
    println("Actividad 2")
    
    testDataClass()
    
    println("---")
    
    println("Actividad 3")  
  
    test1()
    
    test2()
    
    println("---")
    
    println("Actividad 4")  

    test3()
}

/*
    Prueba con valores y nulos
 */
fun test1() {
   var montos = arrayOf<Double?>(10.00, 20.05, null, 10.55, null) 
   var sumaTotalMontos: Double = 0.0;
    
   sumaTotalMontos = sumaTotal(montos)
    
   println("test 1 - La suma total de los montos es: $sumaTotalMontos")
}

/*
    Prueba solo con nulos
 */
fun test2() {
   var montos = arrayOf<Double?>(null, null, null, null, null) 
   var sumaTotalMontos: Double = 0.0;
    
   sumaTotalMontos = sumaTotal(montos)
    
   println("test 2 - La suma total de los montos es: $sumaTotalMontos")
}

fun testDataClass() {
    
    val User1 = UserAccount("1234", "testCorreo@gmail.com", 10.99)
    val User2 = UserAccount("5678", "testCorreo@gmail.com", 776.54)
    
    // Setter
    
    User1.uuid = "9876"
   	User2.email = "User2Email@gmail.com"
    User1.balance = 776.54
    
    // Getter
    
    println(User1.email)
    println(User1.uuid)
    
    // toString
    
    println(User1)
    println(User2)
    
    // Equals
    
    println(User1 == User2)
    println(User1.balance == User2.balance)
    
    // HashCode
    
    println(User1.hashCode())
}

fun sumaTotal(montos: Array<Double?>) : Double {
   var total: Double = 0.0;

   for (monto in montos) total += monto ?: 0.0  

   return total
}

/* Ejercicio 4
 
* Funciones esPar() y esPrimo()
* Creamos la función esPar()
*/
fun Int.esPar(): Boolean{
    return this % 2 == 0
}

/* Creamos la función esPrimo()
* Si el entero en cuestión es divisible por algún número menor, NO es primo. En otro caso sí.
* Se itera sobre los números menores a la mitad del entero en cuestión, los únicos posibles
* divisores.
*/
fun Int.esPrimo(): Boolean{
    // Ningún entero menor o igual que 1 es primo (o compuesto)
    if (this <= 1) return false

    // 2 es primo
    if (this == 2) return true

    // Salvo 2, cualquier natural par es compuesto
    if (this.esPar()) return false

/* Si this tiene un divisor mayor que su raíz cuadrada, necesariamente tendrá otro menor que
   ella (teorema matemático), y si tiene un divisor par distinto de 2, entonces también tiene
   a 2 como divisor; así que basta con buscar algún divisor no mayor que su raíz cuadrada
   entre los impares
*/
for (k in 3..Math.sqrt(this.toDouble()).toInt() step 2){
    if (this % k == 0){
        return false}}

    // Si no se le encontraron otros divisores salvo 1 o this mismo, sí es primo.
    return true
}

// Collections API: 

// Movimientos financieros
data class Transaccion(
    val categoria: String,
    val monto: Double
)

fun reporte(registros: List<Transaccion>): Map<String, Double>{
    return registros.filter { it.monto > 100.0 }                // Descarta registros con monto no mayor a 100
        .groupBy { it.categoria }                                // Agrupa por categoría
        .mapValues { it.value.map { t -> t.monto }.average() }     // Promedia montos
}

fun test3() {
   println("¿5 es par? ${5.esPar()}\n¿12 es par? ${12.esPar()}\n¿-2 es par? ${(-2).esPar()}\n¿13 es primo? ${13.esPrimo()}\n¿10 es primo? ${10.esPrimo()}")
   println("-")

   val myBook = Book().apply {
      title = "Clean Code"
      author = "Robert C. Martin"
      pages = 464
   }

   println("""
      Libro configurado con apply:
      Título: ${myBook.title}
      Autor: ${myBook.author}
      Páginas: ${myBook.pages}
   """.trimIndent())

   println("-")

   // Cuadrados de los números del 1 al 20 que sean múltiplos de 3
   println((1..20).toList()        // Enteros del 1 al 20
        .filter { it % 3 == 0 } // Filtra múltiplos de 3: [3, 6, 9, 12, 15, 18]
        .map { it * it }        // Eleva al cuadrado: [9, 36, 81, 144, 225, 324]
    )
    println("-")

    val movimientos = listOf(
        Transaccion("Comida", 150.0),
        Transaccion("Comida", 50.0),  // No debería entrar (monto < 100)
        Transaccion("Comida", 250.0),
        Transaccion("Salud", 500.0),
        Transaccion("Transporte", 80.0), // No debería entrar
        Transaccion("Salud", 1000.0)
    )

    val analisis = reporte(movimientos)

    println("--- Reporte de Gastos (Promedios > $100) ---")
    analisis.forEach { (cat, promedio) ->
        println("Categoría: $cat | Promedio de gasto: $$promedio")
    }
}