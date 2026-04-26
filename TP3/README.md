El siguiente código debajo es el código del MainActivity.kt cuando se resolvió el ejercicio 3 del trabajo práctico. El código COMPLETO del MainActivity.kt se encuentra en el archivo del mismo nombre, el resultado de los ejercicios 3 y 4 se encuentra en Secuencial.txt, la imagen Ejercicio 2.png es el resultado del ejercicio 2, como bien se intuye por el nombre, y el resultado del ejercicio 5 es el archivo Log de resurreccion.txt.

```import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MainActivity", "onCreate - Creando la aplicacion")

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "onStart - Aplicacion comenzando")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "onPlay - Retomando aplicacion")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "onPause - Aplicacion en pausa")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "onStop - Aplicacion detenida")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "onDestroy - Aplicacion destruida")
    }
}```
