package com.example.reportar.presentation.ui.userMenu

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.reportar.R
import com.example.reportar.presentation.ui.about.AboutFragment
import com.example.reportar.presentation.ui.login.LoginFragment
import com.example.reportar.presentation.viewmodel.ProfileViewModel
import com.example.reportar.presentation.viewmodel.ProfileViewModelFactory
import kotlinx.coroutines.launch

class UserMenuFragment : Fragment(R.layout.fragment_user_menu) {

    private val viewModel: ProfileViewModel by activityViewModels {
        ProfileViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUser = view.findViewById<TextView>(R.id.tvUser)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnInfo = view.findViewById<Button>(R.id.btnInfo)

        lifecycleScope.launch {

            viewModel.state.collect { state ->

                tvUser.text = "Bienvenido ${state.username}"

                if (!state.isLogged) {

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.profileContainer, LoginFragment())
                        .commit()
                }
            }
        }

        btnLogout.setOnClickListener {

            viewModel.logout()
        }

        btnInfo.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.profileContainer, AboutFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}