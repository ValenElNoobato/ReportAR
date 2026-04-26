package com.example.reportar

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var contadorVidas = 0

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
        Log.i("MainActivity", "onResume - Retomando aplicacion")
        contadorVidas++
        Log.i("MainActivity","Estado: onResume ejecutándose - Resurrección número: $contadorVidas")
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
}