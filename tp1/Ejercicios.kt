/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */

data class UserAccount(var uuid: String, var email: String, var balance: Double)

fun main() {
    
    println("Actividad 2")
    
    testDataClass()
    
    println("---")
    
    println("Actividad 3")  
  
    test1()
    
    test2()
    
    println("---")
    
    println("Actividad 4")  
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