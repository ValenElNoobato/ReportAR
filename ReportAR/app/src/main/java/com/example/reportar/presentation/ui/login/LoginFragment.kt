package com.example.reportar.presentation.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.reportar.R
import com.example.reportar.presentation.ui.userMenu.UserMenuFragment
import com.example.reportar.presentation.viewmodel.ProfileViewModel
import com.example.reportar.presentation.viewmodel.ProfileViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: ProfileViewModel by activityViewModels {
        ProfileViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etUser = view.findViewById<EditText>(R.id.etUser)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        lifecycleScope.launch {

            viewModel.state.collect { state ->

                if (state.isLogged) {

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.profileContainer, UserMenuFragment())
                        .commit()
                }

                state.error?.let {

                    Toast.makeText(
                        requireContext(),
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btnLogin.setOnClickListener {

            viewModel.login(
                etUser.text.toString(),
                etPassword.text.toString()
            )
        }
    }
}