package com.example.reportar.presentation.ui.userMenu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.reportar.R
import com.example.reportar.databinding.FragmentUserMenuBinding
import com.example.reportar.presentation.ui.about.AboutFragment
import com.example.reportar.presentation.ui.incident.IncidentListFragment
import com.example.reportar.presentation.ui.login.LoginFragment
import com.example.reportar.presentation.viewmodel.ProfileViewModel
import com.example.reportar.presentation.viewmodel.ProfileViewModelFactory
import kotlinx.coroutines.launch

class UserMenuFragment : Fragment(R.layout.fragment_user_menu) {

    private var _binding: FragmentUserMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by activityViewModels {
        ProfileViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentUserMenuBinding.bind(view)

        observeState()

        setupListeners()
    }

    private fun observeState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->

                    binding.tvUser.text =
                        "Bienvenido ${state.username}"

                    if (!state.isLogged) {

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.profileContainer, LoginFragment())
                            .commit()
                    }
                }
            }
        }
    }

    private fun setupListeners() {

        binding.btnLogout.setOnClickListener {

            viewModel.logout()
        }

        binding.btnInfo.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.profileContainer, AboutFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnIncident.setOnClickListener {
            findNavController().navigate(
                R.id.incidentListFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}