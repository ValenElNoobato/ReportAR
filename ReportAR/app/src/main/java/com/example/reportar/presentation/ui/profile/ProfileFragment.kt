package com.example.reportar.presentation.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.reportar.presentation.ui.login.LoginFragment
import com.example.reportar.R
import com.example.reportar.presentation.ui.userMenu.UserMenuFragment
import com.example.reportar.presentation.viewmodel.ProfileViewModel
import com.example.reportar.presentation.viewmodel.ProfileViewModelFactory
import kotlin.getValue

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory()
    }

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