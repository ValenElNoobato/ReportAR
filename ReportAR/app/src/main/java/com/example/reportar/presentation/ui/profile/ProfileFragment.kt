package com.example.reportar.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.reportar.presentation.ui.login.LoginFragment
import com.example.reportar.R
import com.example.reportar.databinding.FragmentProfileBinding
import com.example.reportar.presentation.ui.userMenu.UserMenuFragment
import com.example.reportar.presentation.viewmodel.ProfileViewModel
import com.example.reportar.presentation.viewmodel.ProfileViewModelFactory
import kotlinx.coroutines.launch
import kotlin.getValue

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProfileBinding.bind(view)

        viewModel.checkSession()

        observeState()
    }

    private fun observeState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->

                    val fragment = if (state.isLogged) {
                        UserMenuFragment()
                    } else {
                        LoginFragment()
                    }

                    childFragmentManager.beginTransaction()
                        .replace(R.id.profileContainer, fragment)
                        .commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}