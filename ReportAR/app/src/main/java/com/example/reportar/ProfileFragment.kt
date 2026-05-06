package com.example.reportar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var prefs: android.content.SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)

        val layoutLogin = view.findViewById<LinearLayout>(R.id.layoutLogin)
        val layoutProfile = view.findViewById<LinearLayout>(R.id.layoutProfile)

        val etUser = view.findViewById<EditText>(R.id.etUser)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        val tvUser = view.findViewById<TextView>(R.id.tvUser)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Verificar sesión al entrar
        val savedUser = prefs.getString("user", null)

        if (savedUser != null) {
            mostrarPerfil(savedUser, layoutLogin, layoutProfile, tvUser)
        } else {
            mostrarLogin(layoutLogin, layoutProfile)
        }

        // Login
        btnLogin.setOnClickListener {
            val user = etUser.text.toString()
            val pass = etPassword.text.toString()

            if (user == "admin" && pass == "123456") {
                prefs.edit().putString("user", user).apply()
                mostrarPerfil(user, layoutLogin, layoutProfile, tvUser)
            } else {
                Toast.makeText(requireContext(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        // Logout
        btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            mostrarLogin(layoutLogin, layoutProfile)
        }
    }

    private fun mostrarPerfil(
        user: String,
        login: LinearLayout,
        profile: LinearLayout,
        tvUser: TextView
    ) {
        login.visibility = View.GONE
        profile.visibility = View.VISIBLE
        tvUser.text = "Bienvenido $user 👋"
    }

    private fun mostrarLogin(
        login: LinearLayout,
        profile: LinearLayout
    ) {
        login.visibility = View.VISIBLE
        profile.visibility = View.GONE
    }
}