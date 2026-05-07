package com.example.practica

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.practica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var contadorVidas = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()
        Log.i("MainActivity", "onCreate - Creando la aplicacion")

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)

        NavigationUI.setupActionBarWithNavController(this, navController)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}