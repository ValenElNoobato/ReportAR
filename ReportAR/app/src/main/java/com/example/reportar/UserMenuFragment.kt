package com.example.reportar

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class UserMenuFragment : Fragment(R.layout.fragment_user_menu) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUser = view.findViewById<TextView>(R.id.tvUser)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnInfo = view.findViewById<Button>(R.id.btnInfo)

        val prefs = requireContext()
            .getSharedPreferences("session", Context.MODE_PRIVATE)

        val user = prefs.getString("user", "Usuario")

        tvUser.text = "Bienvenido $user."

        btnLogout.setOnClickListener {

            prefs.edit().clear().apply()

            parentFragmentManager.beginTransaction()
                .replace(R.id.profileContainer, LoginFragment())
                .commit()
        }

        btnInfo.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.profileContainer, InfoFragment())
                .addToBackStack(null)
                .commit()
        }


    }
}