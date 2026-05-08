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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext()
            .getSharedPreferences("session", Context.MODE_PRIVATE)

        val isLogged = prefs.getString("user", null) != null

        val fragment = if (isLogged) {
            UserMenuFragment()
        } else {
            LoginFragment()
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.profileContainer, fragment)
            .commit()
    }
}