package com.example.reportar

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etUser = view.findViewById<EditText>(R.id.etUser)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val user = etUser.text.toString()
            val pass = etPassword.text.toString()

            if (user == "admin" && pass == "123456") {

                val prefs = requireContext()
                    .getSharedPreferences("session", Context.MODE_PRIVATE)

                prefs.edit().putString("user", user).apply()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.profileContainer, UserMenuFragment())
                    .commit()

            } else {
                Toast.makeText(
                    requireContext(),
                    "Credenciales incorrectas",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}